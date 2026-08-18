export interface PersonForm {
id?: number | null;
  firstName?: string | null;
  lastName?: string | null;
  socialRecordNumber?: string | null;
  birthDate?: string | null; // Use ISO string format (YYYY-MM-DD) for LocalDate in TS
}
