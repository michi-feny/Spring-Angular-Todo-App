export interface CreateTodoRequest {
    title: string;
    description: string;
}

export interface UpdateTodoRequest extends CreateTodoRequest {
    id: number;
    isDone: boolean;
}