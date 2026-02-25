import { Injectable, inject } from '@angular/core';
import { of } from 'rxjs';
import { catchError, concatMap, map, switchMap, withLatestFrom } from 'rxjs/operators';
import * as TodoActions from './todos.actions';
import { TodoService } from '../../../core/servises/TodoService';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Todo } from '../../../types/Todo';
import { ApiResponse } from '../../../types/ApiResponse';
import { select, Store } from '@ngrx/store';
import { TodoState } from './todo.models';
import { selectCurrentPage, selectCurrentSort, selectLoadedPages, selectPageSize } from './todos.selectors';
import { createCacheKey } from './todos.reducer';

@Injectable()
export class TodoEffects {
    private actions$: Actions = inject(Actions);
    private todoService: TodoService = inject(TodoService);
    private store: Store<TodoState> = inject(Store<TodoState>);

    //--- Effect to load all Todos ---
    loadTodos$ = createEffect(() =>
        this.actions$.pipe(
            ofType(TodoActions.loadTodos),
            withLatestFrom(
                this.store.pipe(select(selectLoadedPages)),
                this.store.pipe(select(selectPageSize))
            ),
            concatMap(([action, loadedPages, size]) => {
    
                const normalizedSort = action.sort ?? 'id,desc';
                const cacheKey = createCacheKey(action.page, size, normalizedSort);

                if (loadedPages[cacheKey]) {
                    return of(TodoActions.loadTodosFromCache({
                        todos: loadedPages[cacheKey],
                        page: action.page,
                        size: size,
                        sort: normalizedSort,
                    }));
                }

                return this.todoService.fecthAll(action.page, size, action.sort).pipe(
                    map((response) => TodoActions.loadTodosSuccess({ response })),
                    catchError((error) => of(TodoActions.loadTodosFailure({ error })))
                );
            })
        )
    );

    loadTodosAfterMutation$ = createEffect(() => 
        this.actions$.pipe(
            ofType(
                TodoActions.addTodoSuccess, 
                TodoActions.updateTodoSuccess,
                TodoActions.deleteTodoSuccess
            ),
            
            withLatestFrom(
                this.store.pipe(select(selectCurrentPage)),
                this.store.pipe(select(selectPageSize)),
                this.store.pipe(select(selectCurrentSort))
            ),
            
            map(([action, currentPage, pageSize, currentSort]) =>  {
                const targetPage = action.type === TodoActions.addTodoSuccess.type ? 0 : currentPage;

                return TodoActions.loadTodos({ 
                    page: targetPage, 
                    size: pageSize, 
                    sort: currentSort 
                });
            })
        )
    );
    
    //--- Effect to load single Todo ---
    loadTodo$ = createEffect(() =>
        this.actions$.pipe(
            ofType(TodoActions.loadTodo),
            switchMap((action) =>
                this.todoService.fetch(action.id).pipe(
                    map((todo) => TodoActions.loadTodoSuccess({ todo })),
                    catchError((error) => of(TodoActions.loadTodoFailure({ error })))
                )
            )
        )
    );

    // --- Effect to add a new Todo ---
    addTodo$ = createEffect(() =>
        this.actions$.pipe(
            ofType(TodoActions.addTodo),
            switchMap((action) =>
                this.todoService.create(action.request).pipe(
                    map((todo: Todo) => TodoActions.addTodoSuccess({ todo })),
                    catchError((error) => of(TodoActions.addTodoFailure({ error })))
                )
            )
        )
    );

    // --- Effect to update Todo ---
    toggleComplete$ = createEffect(() =>
        this.actions$.pipe(
            ofType(TodoActions.updateTodo),
            switchMap((action) =>
                this.todoService.update(action.request).pipe(
                    map((todo: Todo) => TodoActions.updateTodoSuccess({ todo })),
                    catchError((error) => of(TodoActions.updateTodoFailure({ error })))
                )
            )
        )
    );

    deleteTodo$ = createEffect(() =>
        this.actions$.pipe(
            ofType(TodoActions.deleteTodo),
            switchMap((action) =>
                this.todoService.delete(action.id).pipe(
                    map((response: ApiResponse<null>) => TodoActions.deleteTodoSuccess({ id: action.id, message: response.message ?? "Todo wurde erfolgreich entfernt" })),
                    catchError((error) => of(TodoActions.deleteTodoFailure({ error })))
                )
            )
        )
    );
}