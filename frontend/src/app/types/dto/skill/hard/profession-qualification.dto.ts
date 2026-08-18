import { HardSkillDto } from './hard-skill.dto';


export interface ProfessionQualificationDto extends HardSkillDto {

    weight: number;

    type: 'PROFESSION';

}