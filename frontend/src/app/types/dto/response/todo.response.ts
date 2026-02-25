import { Todo } from "../../Todo";

export interface TodoListRespone {
    todos: Array<Todo>;
    totalTodos: number;
    currentPage: number;
    sort: string;
    size: number;
}