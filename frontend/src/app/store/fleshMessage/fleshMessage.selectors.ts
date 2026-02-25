import { createFeatureSelector, createSelector } from '@ngrx/store';
import { FlashMessageState } from './fleshMessage.models';
import { fleshMessageFeatureKey } from './fleshMessage.reducer';

export const selectFleshMessageState = createFeatureSelector<FlashMessageState>(fleshMessageFeatureKey);

export const getMessages = createSelector(
    selectFleshMessageState,
    (state) => state.messages
);