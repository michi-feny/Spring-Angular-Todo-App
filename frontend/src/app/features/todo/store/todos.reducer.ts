import { createReducer, on } from '@ngrx/store';
import { initialTodoState, TodoState } from './todo.models';
import * as TodoActions from './todos.actions';

export const todoFeatureKey = 'todo';

export const createCacheKey = (page: number, size: number, sort: string) => `${page}_${size}_${sort}`;

export const todoReducer = createReducer(
    initialTodoState,
    on(TodoActions.loadTodo,
        TodoActions.addTodo,
        TodoActions.updateTodo,
        TodoActions.deleteTodo, 
        (state: TodoState) => ({
            ...state,
            isLoading: true,
            error: null,
        })
    ),

    on(TodoActions.loadTodos, (state: TodoState, { page, size, sort }) => {
        
        const normalizedSort = sort ?? state.currentSort;
        const isNewQuery = state.currentPage !== page || state.currentSort !== normalizedSort;

        return {
            ...state,
            currentPage: page ?? state.currentPage, 
            pageSize: size ?? state.pageSize,
            currentSort: normalizedSort,
            isLoading: true,
            error: null,
            todos: isNewQuery ? [] : state.todos,
        };
    }),


    on(TodoActions.loadTodosFailure,
        TodoActions.loadTodoFailure,
        TodoActions.addTodoFailure,
        TodoActions.updateTodoFailure,
        TodoActions.deleteTodoFailure,
        (state: TodoState, { error }) => ({
            ...state,
            isLoading: false,
            error: error,
        })
    ),
    on(TodoActions.loadTodosFromCache, (state: TodoState, { todos, page, size, sort }) => ({
        ...state,
        todos: todos, 
        currentPage: page,
        pageSize: size,
        currentSort: sort,
        isLoading: false, 
        error: null,
    })),

    // --- Load Todos ---
    on(TodoActions.loadTodosSuccess, (state: TodoState, { response }) => {

        const page = state.currentPage;
        const size = state.pageSize;
        const sort = state.currentSort;

        const queryKey = createCacheKey(page, size, sort);

        const newLoadedPages = {
            ...state.loadedPages,
            [queryKey]: response.todos
        };


        const sortParts = response.sort.split(': ');
        sortParts[1] = sortParts[1].toLocaleLowerCase();

        return {
            ...state,
            todos: response.todos,
            totalTodos: response.totalTodos,
            loadedPages: newLoadedPages,
            isLoading: false,
            error: null,
        };
    }),

    // --- Load Todo ---
    on(TodoActions.loadTodoSuccess, (state: TodoState, { todo }) => {

        const todosWithoutCurrent = state.todos.filter(t => t.id !== todo.id);

        return {
            ...state,
            todos: [
                ...todosWithoutCurrent,
                todo
            ],
            isLoading: false,
            error: null,
        };
    }),


    // --- Add Todo ---
    on(TodoActions.addTodoSuccess, (state: TodoState, { todo }) => ({
        ...state,
        todos: [todo, ...state.todos],
        totalTodos: state.totalTodos !== null ? state.totalTodos + 1 : null,
        loadedPages: {},
        isLoading: false,
        error: null,
    })),

    // --- Update ---
    on(TodoActions.updateTodoSuccess, (state: TodoState, { todo }) => {

        const todosWithoutCurrent = state.todos.filter(t => t.id !== todo.id);
    
        return {
            ...state,
            todos: [...todosWithoutCurrent, todo],
            loadedPages: {},
            isLoading: false,
            error: null,
        };
    }),
    // --- Delete ---
    on(TodoActions.deleteTodoSuccess, (state: TodoState, { id }) => {
        return {
            ...state,
            todos: state.todos.filter(t => t.id !== id),
            totalTodos: state.totalTodos !== null ? state.totalTodos - 1 : null,
            loadedPages: {},
            isLoading: false,
            error: null,
        };
    })
);