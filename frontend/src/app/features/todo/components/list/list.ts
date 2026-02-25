import { Component, inject, OnDestroy, OnInit, signal, ViewChild, WritableSignal } from '@angular/core';
import { Todo } from '../../../../types/Todo';
import { select, Store } from '@ngrx/store';
import { TodoState } from '../../store/todo.models';
import { addTodo, deleteTodo, loadTodos, updateTodo } from '../../store/todos.actions';
import { selectAll, selectCurrentPage, selectCurrentSort, selectPageSize, selectTodoIsLoading, selectTotalTodos } from '../../store/todos.selectors';
import { combineLatest, map, Observable } from 'rxjs';
import { NgbdAccordionStatic } from '../../../../shared/components/bootstrap/accordion-static/accordion-static';
import { CommonModule } from '@angular/common';
import { Modal } from '../../../../shared/components/bootstrap/modal/modal';
import { TodoForm } from '../todo-form/todo-form';
import { CreateTodoRequest, UpdateTodoRequest } from '../../../../types/dto/request/todos.requests';
import { faCheck, faEdit, faRedo, faTrash, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs'
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-list',
  imports: [NgbdAccordionStatic, CommonModule, Modal, TodoForm, FontAwesomeModule, NgbPaginationModule, FormsModule],
  templateUrl: './list.html',
  styleUrl: './list.css',
})
export class List implements OnInit, OnDestroy {
    private store: Store<TodoState> = inject(Store<TodoState>);
    private route: ActivatedRoute = inject(ActivatedRoute);
    private router: Router = inject(Router);
    private routeSubscription!: Subscription;

    public todos$: Observable<Todo[]>;
    public isLoading$: Observable<boolean>;
    public todoToUpdate: WritableSignal<Todo|null> = signal(null);

    public currentPage$: Observable<number>;
    public pageSize$: Observable<number>;
    public currentSort$: Observable<string>;

    public totalTodos$!: Observable<number|null>;

    public faEdit: WritableSignal<IconDefinition> = signal(faEdit);
    public faTrash: WritableSignal<IconDefinition> = signal(faTrash);
    public faCheck: WritableSignal<IconDefinition> = signal(faCheck);
    public faRedo: WritableSignal<IconDefinition> = signal(faRedo);

    @ViewChild('todoEditModal') todoModal!: Modal;
    @ViewChild('todoFormRef') todoFormRef!: TodoForm;

    constructor() {
        this.todos$ = this.store.pipe(select(selectAll));
        this.isLoading$ = this.store.pipe(select(selectTodoIsLoading));
        this.totalTodos$ = this.store.pipe(select(selectTotalTodos));

        this.currentPage$ = this.store.pipe(select(selectCurrentPage));
        this.pageSize$ = this.store.pipe(select(selectPageSize));
        this.currentSort$ = this.store.pipe(select(selectCurrentSort));
    }

    public ngOnInit(): void {
        const queryParams$ = this.route.queryParamMap.pipe(
            map(params => ({
                page: parseInt(params.get('page') || '0', 10),
                sort: params.get('sort') || 'id,desc',
            }))
        );

        this.routeSubscription = combineLatest([
            queryParams$,
            this.pageSize$
        ]).subscribe(([params, pageSize]) => {
            console.log('Component: Dispatching initial loadTodos action with URL params:', params.page, pageSize, params.sort);
            
            this.store.dispatch(loadTodos({ 
                page: params.page, 
                size: pageSize,
                sort: params.sort 
            }));
        });
    }

    public ngOnDestroy(): void {
        this.routeSubscription?.unsubscribe();
    }

    public onPageChange(newPage: number): void {
        this.router.navigate(
            [],
            {
                relativeTo: this.route,
                queryParams: { page: newPage - 1},
                queryParamsHandling: 'merge'
            }
        );
    }

    public onSortChange(sort: string): void {
        this.router.navigate(
            [],
            {
                relativeTo: this.route,
                queryParams: { page: 0, sort: sort}, 
                queryParamsHandling: 'merge'
            }
        );
    }

    public openCreateModal(): void {
        this.todoToUpdate = signal(null);
        this.openModal();
    }

    public openUpdateModal(todo: Todo, event: Event): void {
        this.todoToUpdate = signal(todo);
        this.openModal();
        event.stopPropagation();
    }

    private openModal(): void {
        this.todoModal?.openModal({ size: "lg"});
    }

    public handleDelete(id: number, event: Event): void {
        this.store.dispatch(deleteTodo({ id }));
        event.stopPropagation();
    }

    public handleCreateTodo(): void {
        const request = this.checkForm();

        if(!request) return;

        this.store.dispatch(addTodo({ request: request as CreateTodoRequest }));

        this.todoModal.closeModal();
    }

    public handleUpdateTodo(): void {

        const request = this.checkForm();

        if(!request) return;

        this.store.dispatch(updateTodo({ request: request as UpdateTodoRequest }));

        this.todoModal.closeModal();
    }

    public toggleDone(todo: Todo, event: Event): void {
    
        event.stopPropagation(); 

        this.store.dispatch(updateTodo({ 
            request: {
                id: todo.id,
                isDone: !todo.isDone,
                description: todo.description,
                title: todo.title
            }
        }));
    }

    private checkForm() :CreateTodoRequest|UpdateTodoRequest|void {
        if (!this.todoFormRef) {
            console.error("TodoForm ist noch nicht geladen.");
            return;
        }

        const request = this.todoFormRef.formValue;

        if(!request || !this.todoFormRef.todoForm.valid) {
            console.error("Formular ist ungültig.");
            return;
        }

        return request;
    }
}
