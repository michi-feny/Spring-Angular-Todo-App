import { HardSkillDto } from '../../../../hard-skills/hard-skill.dto';


export interface ProfessionQualificationDto extends HardSkillDto {

    weight: number;

    type: 'PROFESSION';

}