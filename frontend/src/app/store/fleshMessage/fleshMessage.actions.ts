import { createAction, props } from '@ngrx/store';
import { MessageType } from './fleshMessage.models';


export const addFlashMessage = createAction(
    '[Messages] Add Flash Message',
    props<{ messageType: MessageType; text: string; timeout?: number, keepAfterNavigation?: boolean }>()
);

export const addFlashMessageWithId = createAction(
    '[Messages] Add Flash Message With ID',
    props<{ id: number; messageType: MessageType; text: string, keepAfterNavigation: boolean }>()
);

export const removeFlashMessage = createAction(
    '[Messages] Remove Flash Message',
    props<{ id: number }>()
);

export const markMessagesAsExpired = createAction('[Messages] Mark As Expired');

export const clearFlashMessages = createAction('[Messages] Clear All Flash Messages');