import { HardSkillDto } from '../../../../hard-skills/hard-skill.dto';


export interface DegreeDto extends HardSkillDto {

    weight: number;

    preName: boolean;

    postName: boolean;

}