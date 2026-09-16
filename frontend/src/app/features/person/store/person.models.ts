import { PersonData, PersonDto } from "../../../types/dto/person/person-dto";


export interface PersonState {

    searchResults: PersonData[];
    isListLoading: boolean;
    expandedPersonIds: number[];
    detailsCache: Record<number, PersonDto>;
    loadingDetailIds: number[];                  // Liste der IDs, die gerade geladen werden
    error: string | null;
  }
  
  export const initialPersonState: PersonState = {
    searchResults: [],
    isListLoading: false,
    expandedPersonIds: [],
    detailsCache: {},
    loadingDetailIds: [],
    error: null,
  };