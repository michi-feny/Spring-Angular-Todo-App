import { inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { delay, filter, map, mergeMap } from 'rxjs/operators';
import { addFlashMessage, removeFlashMessage, addFlashMessageWithId, clearFlashMessages, markMessagesAsExpired } from './fleshMessage.actions';
import { merge, of, timer } from 'rxjs';
import { NavigationEnd, Router } from '@angular/router';

@Injectable()
export class FleshMessageEffects {
    private actions$: Actions = inject(Actions);
    private router = inject(Router);

    autoRemoveMessage$ = createEffect(() =>
        this.actions$.pipe(
            ofType(addFlashMessage),
            mergeMap(action => {
                const id = Date.now();
                const keepAfterNavigation = action.keepAfterNavigation ?? false;

                const addAction = addFlashMessageWithId({
                    id: id,
                    messageType: action.messageType,
                    text: action.text,
                    keepAfterNavigation
                });

                if (!action.timeout) {
                    return of(addAction);
                }

                return merge(
                    of(addAction), 
                    timer(action.timeout).pipe(map(() => removeFlashMessage({ id })))
                );
            })
        )
    );

    clearMessagesOnNavigation$ = createEffect(() =>
        this.router.events.pipe(
            filter(event => event instanceof NavigationEnd),
            delay(10),
            map(() => markMessagesAsExpired())
        )
    );
}