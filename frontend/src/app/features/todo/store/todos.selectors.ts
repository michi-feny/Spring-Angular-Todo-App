import { createFeatureSelector, createSelector } from '@ngrx/store';
import { TodoState } from './todo.models';
import { todoFeatureKey } from './todos.reducer';
import { Todo } from '../../../types/Todo';

export const selectTodoState = createFeatureSelector<TodoState>(todoFeatureKey);

export const selectAll = createSelector(
    selectTodoState,
    (state) => state.todos
);

export const selectTotalTodos = createSelector(
    selectTodoState,
    (state) => state.totalTodos
);

export const selectPendingTodos = createSelector(
    selectAll,
    (todos) => todos.filter((todo) => !todo.isDone)
);

export const selectCompletedTodos = createSelector(
    selectAll,
    (todos) => todos.filter((todo) => todo.isDone)
);

export const selectTodoByID = (id: number) => createSelector(
    selectAll,
    (todos: Todo[]) => {
        return todos.find((todo) => todo.id === id);
    }
);

export const selectTodoIsLoading = createSelector(
    selectTodoState,
    (state) => state.isLoading
);

export const selectTodoError = createSelector(
    selectTodoState,
    (state) => state.error
);

export const selectCurrentPage = createSelector(
    selectTodoState,
    (state) => state.currentPage
);

export const selectPageSize = createSelector(
    selectTodoState,
    (state) => state.pageSize
);

export const selectCurrentSort = createSelector(
    selectTodoState,
    (state) => state.currentSort
);

export const selectLoadedPages = createSelector(
    selectTodoState,
    (state) => state.loadedPages
);