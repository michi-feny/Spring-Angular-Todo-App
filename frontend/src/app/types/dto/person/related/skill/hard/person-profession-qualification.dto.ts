import { ProfessionQualificationDto } from '../../../../skill/hard/profession-qualification.dto';
import { EducationInstitutionDto } from '../../../../education-institution.dto';


export interface PersonProfessionQualificationDto {

    id?: number;

    profession: ProfessionQualificationDto;

    institution: EducationInstitutionDto;

    startDate: Date;

    endDate?: Date;

}