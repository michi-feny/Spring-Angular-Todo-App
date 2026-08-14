import { HardSkillDto } from '../../../../hard-skills/hard-skill.dto';


export interface AdditionalHardSkillDto extends HardSkillDto {

    category?: string;

    type: 'ADDITIONAL';

}