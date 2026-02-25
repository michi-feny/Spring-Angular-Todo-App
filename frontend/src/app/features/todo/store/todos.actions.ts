import { createAction, props } from '@ngrx/store';
import { Todo } from '../../../types/Todo';
import { CreateTodoRequest, UpdateTodoRequest } from '../../../types/dto/request/todos.requests';
import { TodoListRespone } from '../../../types/dto/response/todo.response';

// --- Load Todos ---
export const loadTodos = createAction(
    '[Todo] Load Todos',
    props<{ page: number, size?: number , sort?: string }>()
);

export const loadTodosSuccess = createAction(
    '[Todo] Load Todos Success',
    props<{ response: TodoListRespone }>()
);

export const loadTodosFailure = createAction(
    '[Todo] Load Todos Failure',
    props<{ error: any }>()
);

export const loadTodosFromCache = createAction(
    '[Todo List] Load Todos From Cache Success',
    props<{ 
        todos: Todo[]; 
        page: number; 
        size: number; 
        sort: string 
    }>()
);

// --- Load Todo ---
export const loadTodo = createAction(
    '[Todo] Load Todo',
    props<{ id: number }>()
);

export const loadTodoSuccess = createAction(
    '[Todo] Load Todo Success',
    props<{ todo: Todo }>()
);

export const loadTodoFailure = createAction(
    '[Todo] Load Todo Failure',
    props<{ error: any }>()
);


// --- Add Todo ---
export const addTodo = createAction(
    '[Todo] Add Todo',
    props<{ request: CreateTodoRequest }>()
);

export const addTodoSuccess = createAction(
    '[Todo] Add Todo Success',
    props<{ todo: Todo }>()
);

export const addTodoFailure = createAction(
    '[Todo] Add Todo Failure',
    props<{ error: any }>()
);

// --- Update Todo ---
export const updateTodo = createAction(
    '[Todo] Update Todo',
    props<{request: UpdateTodoRequest}>()
);

export const updateTodoSuccess = createAction(
    '[Todo] Update Todo Success',
    props<{ todo: Todo }>()
);

export const updateTodoFailure = createAction(
    '[Todo] Update Todo Failure',
    props<{ error: any }>()
);


// --- Delete Todo ---
export const deleteTodo = createAction(
    '[Todo] Delete Todo',
    props<{ id: number }>()
);

export const deleteTodoSuccess = createAction(
    '[Todo] Delete Todo Success',
    props<{ id: number, message: string }>()
);

export const deleteTodoFailure = createAction(
    '[Todo] Delete Todo Failure',
    props<{ error: any }>()
);