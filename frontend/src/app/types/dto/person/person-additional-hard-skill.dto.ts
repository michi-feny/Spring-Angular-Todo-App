import { AdditionalHardSkillDto } from './additional-hard-skill.dto';


export interface PersonAdditionalHardSkillDto {

    id?: number;

    hardSkill: AdditionalHardSkillDto;

    type: 'ADDITIONAL';

}