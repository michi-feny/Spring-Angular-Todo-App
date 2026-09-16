import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators, AbstractControl, FormControl as AngularFormControl } from '@angular/forms';
import { PersonProfessionQualification } from '../../../../../../types/person';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';

@Component({
  selector: 'app-person-professions-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl],
  templateUrl: './person-professions-form.html',
  styleUrl: './person-professions-form.css',
})

export class PersonProfessionsForm {
  private fb = inject(FormBuilder);
  public inputTypes = InputTypesEnum;
  @Input() set professions(values: PersonProfessionQualification[] | undefined) {
    this.setProfessionsFormArray(values || []);
  }

  @Output() save = new EventEmitter<PersonProfessionQualification[]>();

  public form: FormGroup = this.fb.group({
    professions: this.fb.array([])
  });

  get professionsArray(): FormArray {
    return this.form.get('professions') as FormArray;
  }

  getControl(group: AbstractControl, path: string): AngularFormControl {
    return group.get(path) as AngularFormControl;
  }

  private setProfessionsFormArray(professions: PersonProfessionQualification[]): void {
    const formGroups = professions.map((item) => this.createProfessionGroup(item));
    this.form.setControl('professions', this.fb.array(formGroups));
  }

  private createProfessionGroup(item?: PersonProfessionQualification): FormGroup {
    return this.fb.group({
      startDate: [item?.startDate ?? '', Validators.required],
      endDate: [item?.endDate ?? null],
      certificateNumber: [item?.certificateNumber ?? ''],
      professionQualification: this.fb.group({
        id: [item?.professionQualification?.id ?? null],
        name: [item?.professionQualification?.name ?? '', Validators.required],
        level: [item?.professionQualification?.level ?? ''],
        weight: [item?.professionQualification?.weight ?? 1, Validators.required]
      }),
      educationInstitution: this.fb.group({
        id: [item?.educationInstitution?.id ?? null],
        name: [item?.educationInstitution?.name ?? '']
      })
    });
  }

  addProfession(): void {
    this.professionsArray.push(this.createProfessionGroup());
  }

  removeProfession(index: number): void {
    this.professionsArray.removeAt(index);
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.save.emit(this.form.value.professions);
    }
  }
}
