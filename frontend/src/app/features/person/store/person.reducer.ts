import { createReducer, on } from '@ngrx/store';
import * as PersonActions from './person.actions';
import { PersonState, initialPersonState } from './person.models';

export const personFeatureKey = 'person';
function isSameId(id1: any, id2: any): boolean {
  if (!id1 || !id2) return false;
  const keys1 = Object.keys(id1);
  const keys2 = Object.keys(id2);
  if (keys1.length !== keys2.length) return false;
  return keys1.every((key) => id1[key] === id2[key]);
}

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
    loadingDetailIds: state.loadingDetailIds.includes(id)
      ? state.loadingDetailIds
      : [...state.loadingDetailIds, id],
    error: null,
  })),

  on(PersonActions.loadPersonDetailsSuccess, (state, { person }): PersonState => {
    const id = person.person.id!;

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id]: person,
      },

      loadingDetailIds: state.loadingDetailIds.filter((loadingId) => loadingId !== id),
    };
  }),

  on(PersonActions.loadPersonDetailsFailure, (state, { id, error }): PersonState => ({
    ...state,
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

  on(PersonActions.updateGeneralDataSuccess, (state, { data }): PersonState => {
    const id = data.id!;

    const searchResults = state.searchResults.map((item) =>
        item.id === id ? { ...item, ...data } : item
    );

    const existingDetails = state.detailsCache[id];
    const detailsCache = existingDetails
      ? {
          ...state.detailsCache,
          person: {
            ...existingDetails.person,
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
    const personId = data.id?.personId;
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
          phones: [...adjustedPhones, data]
        }
      }
    };
  }),

  on(PersonActions.updatePhoneNumberSuccess, (state, { data }) => {
    const personId = data.id.personId;
    if (!personId || !state.detailsCache[personId]) return state;
  
    const currentPerson = state.detailsCache[personId];
    const currentPhones = currentPerson.phones || [];
    
    const updatedPhoneId = data.id.phoneNumberId;

    const adjustedPhones = data.mainPhoneNumber
      ? currentPhones.map((p) => ({ ...p, mainPhoneNumber: false }))
      : currentPhones;
  
    const updatedPhones = adjustedPhones.map((phone) => {
      return phone.id.phoneNumberId === updatedPhoneId ? data : phone;
    });
  
    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          phones: updatedPhones
        }
      }
    };
  }),

  // --- DELETE PHONE NUMBER ---
  on(PersonActions.deletePhoneNumberSuccess, (state, { id}) => {
    if (!state.detailsCache[id.personId]) return state;
  
    const currentPerson = state.detailsCache[id.personId];
    const currentPhones = currentPerson.phones || [];
  
    const filteredPhones = currentPhones.filter((phone) => {
      return phone.id.phoneNumberId !== id.phoneNumberId;
    });
  
    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id.personId]: {
          ...currentPerson,
          phones: filteredPhones
        }
      }
    };
  }),
  // --- 4. EMAILS ---
  on(PersonActions.createEmailSuccess, (state, { data }) => {
    const personId = data.id.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const currentEmails = currentPerson.emails || [];

    const adjustedEmails = data.mainEmail
      ? currentEmails.map((email) => ({ ...email, mainEmail: false }))
      : currentEmails;

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          emails: [...adjustedEmails, data]
        }
      }
    };
  }),

  on(PersonActions.updateEmailSuccess, (state, { data }) => {
    const personId = data.id.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const currentEmails = currentPerson.emails || [];
    const updatedEmailId = data.id.emailAddressId;

    const adjustedEmails = data.mainEmail
      ? currentEmails.map((e) => ({ ...e, mainEmail: false }))
      : currentEmails;

    const updatedEmails = adjustedEmails.map((email) => {
      return email.id.emailAddressId === updatedEmailId ? data : email;
    });

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          emails: updatedEmails
        }
      }
    };
  }),

  on(PersonActions.deleteEmailSuccess, (state, { id }) => {
    if (!state.detailsCache[id.personId]) return state;

    const currentPerson = state.detailsCache[id.personId];
    const currentEmails = currentPerson.emails || [];

    const filteredEmails = currentEmails.filter((email) => {
      return email.id.emailAddressId !== id.emailAddressId;
    });

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id.personId]: {
          ...currentPerson,
          emails: filteredEmails
        }
      }
    };
  }),

  // --- 5. ADDRESSES ---
  on(PersonActions.createAddressSuccess, (state, { data }) => {
    const personId = data.id.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const currentAddresses = currentPerson.addresses || [];

    const adjustedAddresses = data.mainAddress
      ? currentAddresses.map((addr) => ({ ...addr, mainAddress: false }))
      : currentAddresses;

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          addresses: [...adjustedAddresses, data]
        }
      }
    };
  }),

  on(PersonActions.updateAddressSuccess, (state, { data }) => {
    const personId = data.id.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const currentAddresses = currentPerson.addresses || [];
    const updatedAddressId = data.id.addressId;

    const adjustedAddresses = data.mainAddress
      ? currentAddresses.map((addr) => ({ ...addr, mainAddress: false }))
      : currentAddresses;

    const updatedAddresses = adjustedAddresses.map((address) => {
      return address.id.addressId === updatedAddressId ? data : address;
    });

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          addresses: updatedAddresses
        }
      }
    };
  }),

  on(PersonActions.deleteAddressSuccess, (state, { id }) => {
    if (!state.detailsCache[id.personId]) return state;

    const currentPerson = state.detailsCache[id.personId];
    const currentAddresses = currentPerson.addresses || [];

    const filteredAddresses = currentAddresses.filter((address) => {
      return address.id.addressId !== id.addressId;
    });

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id.personId]: {
          ...currentPerson,
          addresses: filteredAddresses
        }
      }
    };
  }),
  // --- 6. NATIONALITIES ---
  on(PersonActions.createNationalitySuccess, (state, { data }) => {
    const personId = data.id.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const currentNationalities = currentPerson.nationalities || [];

    const adjustedNationalities = data.mainCountry
      ? currentNationalities.map((nat) => ({ ...nat, mainCountry: false }))
      : currentNationalities;

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          nationalities: [...adjustedNationalities, data]
        }
      }
    };
  }),

  on(PersonActions.updateNationalitySuccess, (state, { data }) => {
    const personId = data.id.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const currentNationalities = currentPerson.nationalities || [];
    const updatedCountryId = data.id.countryId;

    const adjustedNationalities = data.mainCountry
      ? currentNationalities.map((nat) => ({ ...nat, mainCountry: false }))
      : currentNationalities;

    const updatedNationalities = adjustedNationalities.map((nat) => {
      return nat.id.countryId === updatedCountryId ? data : nat;
    });

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          nationalities: updatedNationalities
        }
      }
    };
  }),

  on(PersonActions.deleteNationalitySuccess, (state, { id }) => {
    if (!state.detailsCache[id.personId]) return state;

    const currentPerson = state.detailsCache[id.personId];
    const currentNationalities = currentPerson.nationalities || [];

    const filteredNationalities = currentNationalities.filter((nat) => {
      return nat.id.countryId !== id.countryId;
    });

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id.personId]: {
          ...currentPerson,
          nationalities: filteredNationalities
        }
      }
    };
  }),

  on(PersonActions.createSoftSkillSuccess, (state, { data }) => {
    const personId = data.id?.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          softSkills: [...(currentPerson.softSkills || []), data]
        }
      }
    };
  }),

  on(PersonActions.updateSoftSkillSuccess, (state, { data }) => {
    const personId = data.id?.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const updatedList = (currentPerson.softSkills || []).map((item) =>
      isSameId(item.id, data.id) ? data : item
    );

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          softSkills: updatedList
        }
      }
    };
  }),

  on(PersonActions.deleteSoftSkillSuccess, (state, { id }) => {
    if (!state.detailsCache[id.personId]) return state;

    const currentPerson = state.detailsCache[id.personId];
    const filteredList = (currentPerson.softSkills || []).filter(
      (item) => !isSameId(item.id, id)
    );

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id.personId]: {
          ...currentPerson,
          softSkills: filteredList
        }
      }
    };
  }),

  // --- 8. DEGREES ---
  on(PersonActions.createDegreeSuccess, (state, { data }) => {
    const personId = data.id?.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          degrees: [...(currentPerson.degrees || []), data]
        }
      }
    };
  }),

  on(PersonActions.updateDegreeSuccess, (state, { data }) => {
    const personId = data.id?.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const updatedList = (currentPerson.degrees || []).map((item) =>
      isSameId(item.id, data.id) ? data : item
    );

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          degrees: updatedList
        }
      }
    };
  }),

  on(PersonActions.deleteDegreeSuccess, (state, { id }) => {
    if (!state.detailsCache[id.personId]) return state;

    const currentPerson = state.detailsCache[id.personId];
    const filteredList = (currentPerson.degrees || []).filter(
      (item) => !isSameId(item.id, id)
    );

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id.personId]: {
          ...currentPerson,
          degrees: filteredList
        }
      }
    };
  }),

  // --- 9. PROFESSIONS ---
  on(PersonActions.createProfessionSuccess, (state, { data }) => {
    const personId = data.id?.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          professions: [...(currentPerson.professions || []), data]
        }
      }
    };
  }),

  on(PersonActions.updateProfessionSuccess, (state, { data }) => {
    const personId = data.id?.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const updatedList = (currentPerson.professions || []).map((item) =>
      isSameId(item.id, data.id) ? data : item
    );

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          professions: updatedList
        }
      }
    };
  }),

  on(PersonActions.deleteProfessionSuccess, (state, { id }) => {
    if (!state.detailsCache[id.personId]) return state;

    const currentPerson = state.detailsCache[id.personId];
    const filteredList = (currentPerson.professions || []).filter(
      (item) => !isSameId(item.id, id)
    );

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id.personId]: {
          ...currentPerson,
          professions: filteredList
        }
      }
    };
  }),

  // --- 10. ADDITIONAL SKILLS ---
  on(PersonActions.createAdditionalSkillSuccess, (state, { data }) => {
    const personId = data.id?.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          additionalSkills: [...(currentPerson.additionalSkills || []), data]
        }
      }
    };
  }),

  on(PersonActions.updateAdditionalSkillSuccess, (state, { data }) => {
    const personId = data.id?.personId;
    if (!personId || !state.detailsCache[personId]) return state;

    const currentPerson = state.detailsCache[personId];
    const updatedList = (currentPerson.additionalSkills || []).map((item) =>
      isSameId(item.id, data.id) ? data : item
    );

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [personId]: {
          ...currentPerson,
          additionalSkills: updatedList
        }
      }
    };
  }),

  on(PersonActions.deleteAdditionalSkillSuccess, (state, { id }) => {
    if (!state.detailsCache[id.personId]) return state;

    const currentPerson = state.detailsCache[id.personId];
    const filteredList = (currentPerson.additionalSkills || []).filter(
      (item) => !isSameId(item.id, id)
    );

    return {
      ...state,
      detailsCache: {
        ...state.detailsCache,
        [id.personId]: {
          ...currentPerson,
          additionalSkills: filteredList
        }
      }
    };
  }),

  // Ergänze die neuen Failure-Actions im zentralen Error-Handler:
  on(
    PersonActions.createPhoneNumberFailure, PersonActions.updatePhoneNumberFailure, PersonActions.deletePhoneNumberFailure,
    PersonActions.createEmailFailure, PersonActions.updateEmailFailure, PersonActions.deleteEmailFailure,
    PersonActions.createAddressFailure, PersonActions.updateAddressFailure, PersonActions.deleteAddressFailure,
    PersonActions.createNationalityFailure, PersonActions.updateNationalityFailure, PersonActions.deleteNationalityFailure,
    PersonActions.createSoftSkillFailure, PersonActions.updateSoftSkillFailure, PersonActions.deleteSoftSkillFailure,
    PersonActions.createDegreeFailure, PersonActions.updateDegreeFailure, PersonActions.deleteDegreeFailure,
    PersonActions.createProfessionFailure, PersonActions.updateProfessionFailure, PersonActions.deleteProfessionFailure,
    PersonActions.createAdditionalSkillFailure, PersonActions.updateAdditionalSkillFailure, PersonActions.deleteAdditionalSkillFailure,
    (state, { error }) => ({
      ...state,
      error: error?.message || 'Fehler beim Verarbeiten der Formulardaten'
    })
  ),
  
  // --- 5. Reset State ---
  on(PersonActions.resetSelectedPerson, (): PersonState => initialPersonState)
);