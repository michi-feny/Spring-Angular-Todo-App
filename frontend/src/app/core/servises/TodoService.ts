import { inject, Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { map, Observable } from "rxjs";
import { ApiResponse } from "../../types/ApiResponse";
import { Todo } from "../../types/Todo";
import { CreateTodoRequest, UpdateTodoRequest } from "../../types/dto/request/todos.requests";
import { TodoListRespone } from "../../types/dto/response/todo.response";

@Injectable({
    providedIn: 'root'
})
export class TodoService {
    private apiUrl: string = `${environment.apiUrl}`;
    private http: HttpClient = inject(HttpClient);

    fecthAll(page: number, size: number = 10, sort: string ="id,desc"): Observable<TodoListRespone> {
        return this.http
            .get<ApiResponse<TodoListRespone>>(`${this.apiUrl}todos?page=${page}&size=${size}&sort=${sort}`)
            .pipe(map((response) => response.data as TodoListRespone ) );
    }

    fetch(id: number): Observable<Todo> {
        return this.http
            .get<ApiResponse<Todo>>(`${this.apiUrl}todos/${id}`)
            .pipe(map((response) => response.data as Todo));
    }

    create(createTodoRequest: CreateTodoRequest): Observable<Todo> {
        return this.http
            .post<ApiResponse<Todo>>(`${this.apiUrl}todos`, createTodoRequest)
            .pipe(map((response) => response.data as Todo));
    }

    update(updateTodoRequest: UpdateTodoRequest): Observable<Todo> {
        return this.http
        .put<ApiResponse<Todo>>(`${this.apiUrl}todos`, updateTodoRequest)
        .pipe(map((response) => response.data as Todo));
    }

    delete(id: number): Observable<ApiResponse<null>> {
        return this.http.delete<ApiResponse<null>>(`${this.apiUrl}todos/${id}`);
    }
}