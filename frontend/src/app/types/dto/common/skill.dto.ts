import { CompanyDto } from "./common.dto";

export type SkillType =
  | 'DEGREE'
  | 'PROFESSION_QUALIFICATION'
  | 'ADDITIONAL_HARD_SKILL';


export interface HardSkillDto {
  id?: number;
  name: string;
  skillType?: SkillType;
}

export interface AdditionalHardSkillDto extends HardSkillDto {
  level?: string | null;
}

export interface DegreeDto extends HardSkillDto {
  level: string;
  weight: number;
  preName: boolean;
  postName: boolean;
}

export interface ProfessionQualificationDto extends HardSkillDto {
  level?: string | null;
  weight?: number | null;
}

export interface SoftSkillDto {
  id?: number | null;
  name: string;
  description?: string | null;
}

export interface WorkExperienceDto {
  id?: number | null;
  startDate?: string | null;
  endDate?: string | null;
  jobTitle: string;
  description: string;
  militaryService: boolean;
  company?: CompanyDto | null;
}
