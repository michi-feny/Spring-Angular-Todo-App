import { AdditionalHardSkillDto } from '../../../skills/hard/additional-hard-skill.dto';


export interface PersonAdditionalHardSkillDto {

    id?: number;

    hardSkill: AdditionalHardSkillDto;

    type: 'ADDITIONAL';

}