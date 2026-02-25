import { AuthState } from "../features/auth/store/auth.models";
import { FlashMessageState } from "./fleshMessage/fleshMessage.models";

export interface AppState {
    auth: AuthState,
    flashMessage: FlashMessageState
}