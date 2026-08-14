export type HardSkillType =
    | 'DEGREE'
    | 'PROFESSION'
    | 'ADDITIONAL';

export interface HardSkillDto {

    id?: number;

    name: string;

    /**
     * JPA discriminator value
     */
    type: HardSkillType;

}