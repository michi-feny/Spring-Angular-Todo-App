import { SoftSkillDto } from "../../../../skill/soft/soft-skill-dto";
import { PersonSoftSkillDtoId } from "../../reference/skill/person-soft-skill-dto-id";

export interface PersonSoftSkillDto {
    id: PersonSoftSkillDtoId; 
    softSkill: SoftSkillDto;
}
