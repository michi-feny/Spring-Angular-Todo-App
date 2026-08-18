import { ProfessionQualificationDto } from '../../../../skill/hard/profession-qualification.dto';
import { EducationInstitutionDto } from '../../../../education-institution.dto';
import { PersonPhoneNumberDtoId } from '../../reference/contact/person-phone-number-dto-id';


export interface PersonProfessionQualificationDto {

    id?: PersonPhoneNumberDtoId;

    professionQualification: ProfessionQualificationDto;

    educationInstitution: EducationInstitutionDto;

    startDate: Date;

    endDate?: Date;

    certificateNumber: string;

    name: string;

    

}