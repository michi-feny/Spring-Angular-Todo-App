import { HardSkillDto } from './hard-skill.dto';


export interface DegreeDto extends HardSkillDto {

    weight: number;

    preName: boolean;

    postName: boolean;

    type: 'DEGREE';

}