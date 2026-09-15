import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl as AngularFormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { PersonCountryDto } from '../../../../../../types/dto/person/related/contact/country/person-country-dto';
import { PersonCountryDtoId } from '../../../../../../types/dto/person/related/reference/contact/person-country-dto-id';

@Component({
  selector: 'app-person-nationalities-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './person-nationalities-form.html'
})
export class PersonNationalitiesForm {
  private fb = inject(FormBuilder);
  public inputTypes = InputTypesEnum;

  @Input({ required: true }) personId!: number | undefined;
  @Input() set nationalities(values: PersonCountryDto[] | undefined) {
    this.setNationalitiesFormArray(values || []);
  }

  @Output() save = new EventEmitter<PersonCountryDto>();
  @Output() delete = new EventEmitter<{ id: PersonCountryDtoId }>();

  public form: FormGroup = this.fb.group({
    nationalities: this.fb.array([])
  });

  get nationalitiesArray(): FormArray {
    return this.form.get('nationalities') as FormArray;
  }

  getGroup(index: number): FormGroup {
    return this.nationalitiesArray.at(index) as FormGroup;
  }

  getControl(group: AbstractControl, path: string): AngularFormControl {
    return group.get(path) as AngularFormControl;
  }

  private setNationalitiesFormArray(nationalities: PersonCountryDto[]): void {
    const formGroups = nationalities.map((item) => this.createNationalityGroup(item));
    this.form.setControl('nationalities', this.fb.array(formGroups));
  }

  private createNationalityGroup(item?: Partial<PersonCountryDto>): FormGroup {
    return this.fb.group({
      id: this.fb.group({
        personId: [item?.id?.personId ?? this.personId, [Validators.required, Validators.min(1)]],
        countryId: [item?.id?.countryId ?? null, [Validators.required, Validators.min(1)]]
      }),
      mainCountry: [item?.mainCountry ?? false, Validators.required]
    });
  }

  addNationality(): void {
    if (!this.personId) return;

    this.nationalitiesArray.push(
      this.createNationalityGroup({
        id: {
          personId: this.personId,
          countryId: undefined
        },
        mainCountry: this.nationalitiesArray.length === 0
      })
    );
  }

  deleteNationality(index: number): void {
    const group = this.getGroup(index);
    const rawValue = group.getRawValue();

    const personId = Number(rawValue.id?.personId || this.personId);
    const countryId = rawValue.id?.countryId ? Number(rawValue.id.countryId) : undefined;

    if (personId && countryId) {
      this.delete.emit({
        id: {
          personId,
          countryId
        }
      });
    } else {
      this.nationalitiesArray.removeAt(index);
    }
  }

  setMainCountry(selectedIndex: number): void {
    this.nationalitiesArray.controls.forEach((control, index) => {
      control.get('mainCountry')?.setValue(index === selectedIndex, { emitEvent: false });
    });
  }

  saveSingleNationality(index: number): void {
    const group = this.getGroup(index);
    if (group.invalid) return;

    const rawValue = group.getRawValue();
    const targetPersonId = rawValue.id?.personId || this.personId;

    if (!targetPersonId) {
      console.error('Speichern abgebrochen: personId ist nicht gesetzt!');
      return;
    }

    const countryId = rawValue.id?.countryId ? Number(rawValue.id.countryId) : undefined;

    const dto: PersonCountryDto = {
      id: {
        personId: Number(targetPersonId),
        countryId: countryId!
      },
      mainCountry: Boolean(rawValue.mainCountry)
    };

    this.save.emit(dto);
  }
}