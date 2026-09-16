import { EducationInstitutionDto, LocalDateDurationDto } from "../common/common.dto";
import { AdditionalHardSkillDto, DegreeDto, ProfessionQualificationDto, SoftSkillDto, WorkExperienceDto } from "../common/skill.dto";
import { PersonAdditionalHardSkillDtoId, PersonDegreeDtoId, PersonProfessionQualificationDtoId, PersonSoftSkillDtoId, PersonWorkExperienceDtoId } from "./person-id.dto";

export interface PersonAdditionalHardSkillDto {
  id?: PersonAdditionalHardSkillDtoId | null;
  personAdditionalHardSkillDto: AdditionalHardSkillDto;
}

export interface PersonDegreeDto {
  id?: PersonDegreeDtoId | null;
  degree: DegreeDto;
  educationInstitution: EducationInstitutionDto;
  degreeDuration: LocalDateDurationDto;
  progressInPercent: number;
}

export interface PersonProfessionQualificationDto {
  id?: PersonProfessionQualificationDtoId | null;
  educationInstitution: EducationInstitutionDto;
  professionQualification: ProfessionQualificationDto;
  professionQualificationDuration: LocalDateDurationDto;
  certificateNumber?: string | null;
}

export interface PersonWorkExperienceDto {
  id?: PersonWorkExperienceDtoId | null;
  workExperience: WorkExperienceDto;
  displayOrder: number;
  visible: boolean;
  mergedIntoWorkExpId?: number | null;
}

export interface PersonSoftSkillDto {
  id?: PersonSoftSkillDtoId | null;
  softSkill: SoftSkillDto;
}

export interface MergeWorkExperiencesRequestDto {
  personId: number;
  workExpIdsToMerge: number[];
  newMasterDetails: WorkExperienceDto;
}
