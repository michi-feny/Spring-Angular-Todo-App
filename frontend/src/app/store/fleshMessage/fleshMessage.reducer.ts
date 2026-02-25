import { createReducer, on } from '@ngrx/store';
import * as MessageActions from './fleshMessage.actions';
import { initialFleshMessageState } from './fleshMessage.models';

export const fleshMessageFeatureKey = 'fleshMessage';

export const fleshMessageReducer = createReducer(
    initialFleshMessageState,

    on(MessageActions.addFlashMessageWithId, (state, { id, messageType, text, keepAfterNavigation }) => {

        if (state.messages.some(m => m.text === text)) return state;
    
        return {
            ...state,
            messages: [...state.messages, { id, messageType, text, keepAfterNavigation }]
        };
    }),

    on(MessageActions.markMessagesAsExpired, (state) => ({
        ...state,
        messages: state.messages
            .filter(m => m.keepAfterNavigation)
            .map(m => ({ ...m, keepAfterNavigation: false }))
    })),
    
    on(MessageActions.removeFlashMessage, (state, { id }) => ({
        ...state,
        messages: state.messages.filter(msg => msg.id !== id)
    })),

    on(MessageActions.clearFlashMessages, (state) => ({
        ...state,
        messages: []
    })),
);