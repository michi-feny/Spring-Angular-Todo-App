import { AuthState } from "../features/auth/store/auth.models";
import { TodoState } from "../features/todo/store/todo.models";
import { FlashMessageState } from "./fleshMessage/fleshMessage.models";

export interface AppState {
    auth: AuthState,
    todo: TodoState,
    flashMessage: FlashMessageState
}