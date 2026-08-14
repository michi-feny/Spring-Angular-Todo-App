import { DegreeDto } from '../../../skills/hard/degree.dto';
import { EducationInstitutionDto } from '../../../../education-institution.dto';


export interface PersonDegreeDto {

    id?: number;

    degree: DegreeDto;

    institution: EducationInstitutionDto;

    startDate: Date;

    endDate?: Date;

    progressInPercent: number;

    type: 'DEGREE';

}