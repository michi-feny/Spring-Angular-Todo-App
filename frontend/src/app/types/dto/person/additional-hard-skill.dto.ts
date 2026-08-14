import { HardSkillDto } from './hard-skill.dto';


export interface AdditionalHardSkillDto extends HardSkillDto {

    category?: string;

    type: 'ADDITIONAL';

}