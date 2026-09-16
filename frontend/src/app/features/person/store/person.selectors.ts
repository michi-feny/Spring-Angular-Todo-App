import { createFeatureSelector, createSelector } from '@ngrx/store';
import { PersonState } from './person.models';
import { personFeatureKey } from './person.reducer';

export const selectPersonState = createFeatureSelector<PersonState>(personFeatureKey);

export const selectPersonList = createSelector(
  selectPersonState,
  (state) => state.searchResults
);

export const selectIsListLoading = createSelector(
  selectPersonState,
  (state) => state.isListLoading
);

export const selectExpandedPersonIds = createSelector(
  selectPersonState,
  (state) => state.expandedPersonIds
);

export const selectPersonDetailsCache = createSelector(
  selectPersonState,
  (state) => state.detailsCache
);

export const selectLoadingDetailIds = createSelector(
  selectPersonState,
  (state) => state.loadingDetailIds
);

export const selectPersonError = createSelector(
  selectPersonState,
  (state) => state.error
);


export const selectPersonDetailsById = (id: number) =>
  createSelector(
    selectPersonDetailsCache,
    (cache) => cache[id] ?? null
  );


export const selectIsPersonLoading = (id: number) =>
  createSelector(
    selectLoadingDetailIds,
    (loadingIds) => loadingIds.includes(id)
  );


export const selectIsPersonExpanded = (id: number) =>
  createSelector(
    selectExpandedPersonIds,
    (expandedIds) => expandedIds.includes(id)
  );