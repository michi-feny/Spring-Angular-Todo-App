import { DegreeDto } from '../../../../skill/hard/degree.dto';
import { EducationInstitutionDto } from '../../../../education-institution.dto';
import { PersonDegreeDtoId } from '../../reference/skill/person-degree-dto-id';


export interface PersonDegreeDto {

    id?: PersonDegreeDtoId;

    degree: DegreeDto;

    educationInstitution: EducationInstitutionDto;

    startDate: Date;

    endDate?: Date;

    progressInPercent: number;

}