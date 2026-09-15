import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, FormControl as AngularFormControl } from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { PersonData } from '../../../../../../types/dto/person/person-data';
import { PersonDto } from '../../../../../../types/dto/person/person.dto';

@Component({
  selector: 'app-person-general-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl],
  templateUrl: './person-general-form.html'
})
export class PersonGeneralForm {
  private fb = inject(FormBuilder);
  public inputTypes = InputTypesEnum;

  @Input() set person(personDto: PersonData | null | undefined) {
    if (personDto) {
      this.populateForm(personDto);
    }
  }

  @Output() save = new EventEmitter<PersonData>();

  public form: FormGroup = this.fb.group({
    id: [null as number | null],
    socialRecordNumber: [null as number | null],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    birthDate: ['', Validators.required]
  });

  getControl(path: string): AngularFormControl {
    return this.form.get(path) as AngularFormControl;
  }

  private populateForm(person: PersonData): void {
    let formattedDate = '';
    if (person.birthDate) {
      const dateObj = new Date(person.birthDate);
      if (!isNaN(dateObj.getTime())) {
        formattedDate = dateObj.toISOString().split('T')[0];
      }
    }
    console.log(person)
    this.form.patchValue({
      id: person.id,
      socialRecordNumber: person.socialRecordNumber,
      firstName: person.firstName,
      lastName: person.lastName,
      birthDate: formattedDate
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.save.emit(this.form.getRawValue() as PersonData);
  }
}