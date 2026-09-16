// ==========================================
// BASE & STAMMDATEN ENTITIES
// ==========================================

export interface Country {
    id?: number;
    code: string;       // ISO 3166-1 Alpha-2
    name: string;
    language?: string;
  }
  
  export interface Address {
    id?: number;
    street: string;
    houseNumber: string;
    zipCode: string;
    city: string;
    country: Country;
  }
  
  export interface PhoneNumber {
    id?: number;
    phoneNumber: string; // Ohne führende 0
    countryCode: string; // z.B. "+43"
    fullNumber?: string;
  }
  
  export interface EmailAddress {
    id?: number;
    emailAddress: string;
  }
  
  export interface EducationInstitution {
    id?: number;
    name: string;
    address?: Address;
  }
  
  // ==========================================
  // SKILL HIERARCHIE (HardSkill Inheritance)
  // ==========================================
  
  export interface HardSkill {
    id?: number;
    name: string;   // z.B. "Master of Science", "Führerschein B"
    level?: string;  // z.B. "MSc", "C1", "Meister"
  }
  
  export interface Degree extends HardSkill {
    weight: number;
    preName: boolean;
    postName: boolean;
  }
  
  export interface ProfessionQualification extends HardSkill {
    weight: number;
  }
  
  export interface AdditionalHardSkill extends HardSkill {
    category?: string; // z.B. "LANGUAGE", "IT", "DRIVING"
  }
  
  export interface SoftSkill {
    id?: number;
    name: string;
  }
  
  // ==========================================
  // PERSON JOIN ENTITIES (1:N / N:M Relations)
  // ==========================================
  
  export interface PersonCountry {
    country: Country;
    mainCountry: boolean;
  }
  
  export interface PersonAddress {
    address: Address;
    mainAddress: boolean;
  }
  
  export interface PersonPhoneNumber {
    phoneNumber: PhoneNumber;
    mainNumber: boolean;
  }
  
  export interface PersonEmailAddress {
    emailAddress: EmailAddress;
    mainEmail: boolean;
  }
  
  export interface PersonDegree {
    degree: Degree;
    institution: EducationInstitution;
    startDate: string; // ISO Date String: yyyy-MM-dd
    endDate?: string | null;
    progressInPercent: number;
  }
  
  export interface PersonProfessionQualification {
    professionQualification: ProfessionQualification;
    educationInstitution?: EducationInstitution;
    startDate: string;
    endDate?: string | null;
    certificateNumber?: string;
  }
  
  export interface PersonAdditionalHardSkill {
    additionalHardSkill: AdditionalHardSkill;
  }
  
  export interface PersonSoftSkill {
    softSkill: SoftSkill;
  }
  
  // ==========================================
  // PERSON MAIN ENTITY
  // ==========================================
  
  export interface Person {
    id?: number;
    socialRecordNumber?: number;
    firstName: string;
    lastName: string;
    birthDate: string; // ISO Date String
  
    nationalitys: PersonCountry[];
    addresses: PersonAddress[];
    phones: PersonPhoneNumber[];
    emails: PersonEmailAddress[];
    degrees: PersonDegree[];
    professions: PersonProfessionQualification[];
    additionalSkills: PersonAdditionalHardSkill[];
    softSkills: PersonSoftSkill[];
  }