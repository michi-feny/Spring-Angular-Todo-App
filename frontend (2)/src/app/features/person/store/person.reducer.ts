import { createReducer, on } from '@ngrx/store';
import * as PersonActions from './person.actions';
import { PersonState, initialPersonState } from './person.models';

export const personFeatureKey = 'person';

export const personReducer = createReducer(
  initialPersonState,

  // --- 1. Load Person List ---
  on(PersonActions.loadPersonList, (state): PersonState => ({
    ...state,
    isListLoading: true,
    error: null,
  })),

  on(PersonActions.loadPersonListSuccess, (state, { persons }): PersonState => ({
    ...state,
    searchResults: persons,
    isListLoading: false,
  })),

  on(PersonActions.loadPersonListFailure, (state, { error }): PersonState => ({
    ...state,
    isListLoading: false,
    error,
  })),

  on(PersonActions.loadPersonDetails, (state, { id }): PersonState => ({
    ...state,
    // ID in loadingDetailIds aufnehmen, um Spinner an der Zeile zu zeigen
    loadingDetailIds: state.loadingDetailIds.includes(id)
      ? state.loadingDetailIds
      : [...state.loadingDetailIds, id],
    error: null,
  })),

  on(PersonActions.loadPersonDetailsSuccess, (state, { person }): PersonState => {
    const id = person.person.id!;
    console.log(person);
    return {
      ...state,
      // In Cache ablegen
      detailsCache: {
        ...state.detailsCache,
        [id]: person,
      },
      // ID aus Lade-Liste entfernen
      loadingDetailIds: state.loadingDetailIds.filter((loadingId) => loadingId !== id),
    };
  }),

  on(PersonActions.loadPersonDetailsFailure, (state, { id, error }): PersonState => ({
    ...state,
    // Bei Fehler Spinner entfernen
    loadingDetailIds: state.loadingDetailIds.filter((loadingId) => loadingId !== id),
    error,
  })),

  on(PersonActions.togglePersonAccordion, (state, { id }): PersonState => {
    const isExpanded = state.expandedPersonIds.includes(id);
    const expandedPersonIds = isExpanded
      ? state.expandedPersonIds.filter((expId) => expId !== id)
      : [...state.expandedPersonIds, id];

    return {
      ...state,
      expandedPersonIds,
    };
  }),

  // --- 4. Update General Data ---
  on(PersonActions.updateGeneralDataSuccess, (state, { data }): PersonState => {
    const id = data.id!;

    // 1. Aktualisiere den Eintrag in der Übersicht/Suche
    const searchResults = state.searchResults.map((item) =>
        item.id === id ? { ...item, ...data } : item
      );

    // 2. Aktualisiere den Eintrag im Details-Cache (falls bereits geladen)
    const existingDetails = state.detailsCache[id];
    const detailsCache = existingDetails
      ? {
          ...state.detailsCache,
          [id]: {
            ...existingDetails,
            ...data,
          },
        }
      : state.detailsCache;

    return {
      ...state,
      searchResults,
      detailsCache,
    };
  }),

  on(PersonActions.updateGeneralDataFailure, (state, { error }): PersonState => ({
    ...state,
    error,
  })),

  on(PersonActions.createPhoneNumberSuccess, (state, { data }) => {
    const personId = data.id.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const currentPhones = currentPerson.phones || [];

    const adjustedPhones = data.mainPhoneNumber
      ? currentPhones.map((phone) => ({ ...phone, mainPhoneNumber: false }))
      : currentPhones;

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          personPhoneNumberDtos: [...adjustedPhones, data]
        }
      }
    };
  }),

  on(PersonActions.createPhoneNumberFailure, PersonActions.updatePhoneNumberFailure,
    PersonActions.deletePhoneNumberFailure, (state, { error }) => ({
    ...state,
    error: error?.message || 'Fehler beim Erstellen der Telefonnummer'
  })),

  on(PersonActions.updatePhoneNumberSuccess, (state, { data }) => {
    const personId = data.id.personId;
    if (!personId || !state.detailsCache[personId]) return state;
  
    const currentPerson = state.detailsCache[personId];
    const currentPhones = currentPerson.phones || [];
    
    // Zuerst die DTO-ID prüfen, dann erst die untergeordnete Telefon-ID
    const updatedPhoneId = data.id ?? data.phoneNumber?.id;
  
    // Falls die aktualisierte Nummer Hauptnummer wird, andere zurücksetzen
    const adjustedPhones = data.mainPhoneNumber
      ? currentPhones.map((p) => ({ ...p, mainPhoneNumber: false }))
      : currentPhones;
  
    // Telefonnummer in der Liste ersetzen
    const updatedPhones = adjustedPhones.map((phone) => {
      const currentId = phone.id ?? phone.phoneNumber?.id;
      return currentId === updatedPhoneId ? data : phone;
    });
  
    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          personPhoneNumberDtos: updatedPhones
        }
      }
    };
  }),

  // --- DELETE PHONE NUMBER ---
  on(PersonActions.deletePhoneNumberSuccess, (state, { id}) => {
    if (!state.detailsCache[id.personId]) return state;
  
    const currentPerson = state.detailsCache[id.personId];
    const currentPhones = currentPerson.phones || [];
  
    // Gelöschte Nummer aus dem Array filtern
    const filteredPhones = currentPhones.filter((phone) => {
      const currentId = phone.id.phoneNumberId ?? phone.phoneNumber?.id;
      return currentId !== id.phoneNumberId;
    });
  
    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id.personId]: {
          ...currentPerson,
          personPhoneNumberDtos: filteredPhones
        }
      }
    };
  }),

  // --- 5. Reset State ---
  on(PersonActions.resetSelectedPerson, (): PersonState => initialPersonState)
);