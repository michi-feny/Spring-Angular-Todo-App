import { Todo } from "../../../types/Todo";

export interface TodoState {
    todos: Todo[];
    totalTodos: number|null;
    currentPage: number;
    pageSize: number;
    currentSort: string;
    loadedPages: { [queryKey: string]: Todo[] }
    isLoading: boolean;
    error: any;
}

export const initialTodoState: TodoState = {
    todos: [],
    totalTodos: null,
    currentPage: 0,
    pageSize: 10,
    currentSort: 'id,desc',
    loadedPages: {},
    isLoading: false,
    error: null,
};