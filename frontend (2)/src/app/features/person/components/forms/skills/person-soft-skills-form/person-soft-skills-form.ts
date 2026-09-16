import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators, AbstractControl, FormControl as AngularFormControl } from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { PersonSoftSkill } from '../../../../../../types/person';


@Component({
  selector: 'app-person-soft-skills-form',
  imports: [CommonModule, ReactiveFormsModule, AppFormControl],
  templateUrl: './person-soft-skills-form.html',
  styleUrl: './person-soft-skills-form.css',
})
export class PersonSoftSkillsForm {

  private fb = inject(FormBuilder);
  public inputTypes = InputTypesEnum;

  @Input() set softSkills(values: PersonSoftSkill[] | undefined) {
    this.setSkillsFormArray(values || []);
  }

  @Output() save = new EventEmitter<PersonSoftSkill[]>();

  public form: FormGroup = this.fb.group({
    softSkills: this.fb.array([])
  });

  get softSkillsArray(): FormArray {
    return this.form.get('softSkills') as FormArray;
  }

  getControl(group: AbstractControl, path: string): AngularFormControl {
    return group.get(path) as AngularFormControl;
  }

  private setSkillsFormArray(skills: PersonSoftSkill[]): void {
    const formGroups = skills.map((item) => this.createSkillGroup(item));
    this.form.setControl('softSkills', this.fb.array(formGroups));
  }

  private createSkillGroup(item?: PersonSoftSkill): FormGroup {
    return this.fb.group({
      softSkill: this.fb.group({
        id: [item?.softSkill?.id ?? null],
        name: [item?.softSkill?.name ?? '', Validators.required]
      })
    });
  }

  addSoftSkill(): void {
    this.softSkillsArray.push(this.createSkillGroup());
  }

  removeSoftSkill(index: number): void {
    this.softSkillsArray.removeAt(index);
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.save.emit(this.form.value.softSkills);
    }
  }
}
