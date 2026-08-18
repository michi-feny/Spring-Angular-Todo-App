import { AdditionalHardSkillDto } from '../../../../skill/hard/additional-hard-skill.dto';


export interface PersonAdditionalHardSkillDto {

    id?: number;

    hardSkill: AdditionalHardSkillDto;

    type: 'ADDITIONAL';

}