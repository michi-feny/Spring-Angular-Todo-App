export enum MessageType {
    Success = 'success',
    Danger = 'danger',
    Info = 'info',
    Warning = 'warning',
    Primary = 'primary'
}

export interface FlashMessage {
    id: number;
    text: string;
    messageType: MessageType;
    keepAfterNavigation: boolean;
}

export interface FlashMessageState {
    messages: FlashMessage[];
}

export const initialFleshMessageState: FlashMessageState = {
    messages: []
};