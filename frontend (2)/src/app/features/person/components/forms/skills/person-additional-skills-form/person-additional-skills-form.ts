import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators, AbstractControl, FormControl as AngularFormControl } from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { PersonAdditionalHardSkill } from '../../../../../../types/person';
import { PersonAdditionalHardSkillDto } from '../../../../../../types/dto/person/related/skill/hard/person-additional-hard-skill.dto';
/*
  export interface PersonAdditionalHardSkill {
    additionalHardSkill: AdditionalHardSkill;
  }

    export interface AdditionalHardSkill extends HardSkill {
      category?: string; // z.B. "LANGUAGE", "IT", "DRIVING"
    }
    export interface HardSkill {
      id?: number;
      name: string;   // z.B. "Master of Science", "Führerschein B"
      level?: string;  // z.B. "MSc", "C1", "Meister"
    }
*/
@Component({
  selector: 'app-person-additional-skills-form',
  imports: [CommonModule, ReactiveFormsModule, AppFormControl],
  templateUrl: './person-additional-skills-form.html',
  styleUrl: './person-additional-skills-form.css',
})
export class PersonAdditionalSkillsForm {
  private fb = inject(FormBuilder);
  public inputTypes = InputTypesEnum;

  @Input() set additionalSkills(values: PersonAdditionalHardSkillDto[] | undefined) {
    this.setSkillsFormArray(values || []);
  }

  @Output() save = new EventEmitter<PersonAdditionalHardSkillDto[]>();

  public form: FormGroup = this.fb.group({
    additionalSkills: this.fb.array([])
  });

  get additionalSkillsArray(): FormArray {
    return this.form.get('additionalSkills') as FormArray;
  }

  getControl(group: AbstractControl, path: string): AngularFormControl {
    return group.get(path) as AngularFormControl;
  }

  private setSkillsFormArray(skills: PersonAdditionalHardSkillDto[]): void {
    const formGroups = skills.map((item) => this.createSkillGroup(item));
    this.form.setControl('additionalSkills', this.fb.array(formGroups));
  }

  private createSkillGroup(item?: PersonAdditionalHardSkillDto): FormGroup {
    const skill = item?.personAdditionalHardSkillDto
    return this.fb.group({
      additionalHardSkill: this.fb.group({
        id: [skill?.id ?? null],
        name: [skill?.name ?? '', Validators.required],
        //level: [skill?.level ?? ''],
        category: [skill?.category ?? '']
      })
    });
  }

  addSkill(): void {
    this.additionalSkillsArray.push(this.createSkillGroup());
  }

  removeSkill(index: number): void {
    this.additionalSkillsArray.removeAt(index);
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.save.emit(this.form.value.additionalSkills);
    }
  }
}
