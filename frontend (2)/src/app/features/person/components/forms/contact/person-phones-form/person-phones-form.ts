import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, FormArray, FormControl as AngularFormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { PersonPhoneNumberDto } from '../../../../../../types/dto/person/related/contact/phone/person-phone-number.dto';
import { PersonPhoneNumberDtoId } from '../../../../../../types/dto/person/related/reference/contact/person-phone-number-dto-id';

@Component({
  selector: 'app-person-phones-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl],
  templateUrl: './person-phones-form.html'
})
export class PersonPhonesForm {
  private fb = inject(FormBuilder);
  public inputTypes = InputTypesEnum;

  @Input({ required: true }) personId!: number | undefined;
  @Input() set phones(values: PersonPhoneNumberDto[]) {
    this.setPhonesFormArray(values || []);
  }

  @Output() save = new EventEmitter<PersonPhoneNumberDto>();
  @Output() delete = new EventEmitter<{ id: PersonPhoneNumberDtoId }>();

  public form: FormGroup = this.fb.group({
    phones: this.fb.array([])
  });

  get phonesArray(): FormArray {
    return this.form.get('phones') as FormArray;
  }

  getGroup(index: number): FormGroup {
    return this.phonesArray.at(index) as FormGroup;
  }

  getControl(group: AbstractControl, path: string): AngularFormControl {
    return group.get(path) as AngularFormControl;
  }

  private setPhonesFormArray(phones: PersonPhoneNumberDto[]): void {
    const formGroups = phones.map((item) => this.createPhoneGroup(item));
    this.form.setControl('phones', this.fb.array(formGroups));
  }

  private createPhoneGroup(item?: Partial<PersonPhoneNumberDto>): FormGroup {
    return this.fb.group({
      id: this.fb.group({
        personId: [item?.id?.personId ?? this.personId, [Validators.required, Validators.min(1)]],
        phoneNumberId: [item?.id?.phoneNumberId ?? item?.phoneNumber?.id ?? null]
      }),
      mainPhoneNumber: [item?.mainPhoneNumber ?? false, Validators.required],
      phoneNumber: this.fb.group({
        id: [item?.phoneNumber?.id ?? item?.id?.phoneNumberId ?? null],
        countryCode: [item?.phoneNumber?.countryCode ?? '', [Validators.required, Validators.maxLength(4)]],
        phoneNumber: [item?.phoneNumber?.phoneNumber ?? '', [Validators.required, Validators.maxLength(50)]],
        fullNumber: [item?.phoneNumber?.fullNumber ?? ''],
        nationalityId: [item?.phoneNumber?.nationalityId ?? 1, [Validators.required, Validators.min(1)]],
      })
    });
  }

  addPhone(): void {
    if (!this.personId) return;

    this.phonesArray.push(
      this.createPhoneGroup({
        id: {
          personId: this.personId,
          phoneNumberId: undefined
        },
        mainPhoneNumber: this.phonesArray.length === 0,
        phoneNumber: {
          id: undefined,
          countryCode: '+43',
          phoneNumber: '',
          fullNumber: '',
          nationalityId: 1
        }
      })
    );
  }

  deletePhone(index: number): void {
    const group = this.getGroup(index);
    const rawValue = group.getRawValue();

    const personId = Number(rawValue.id?.personId || this.personId);
    const phoneNumberId = rawValue.id?.phoneNumberId ? Number(rawValue.id.phoneNumberId) : (rawValue.phoneNumber?.id ? Number(rawValue.phoneNumber.id) : undefined);

    if (personId && phoneNumberId) {
      this.delete.emit({
        id: {
          personId,
          phoneNumberId
        }
      });
    } else {
      this.phonesArray.removeAt(index);
    }
  }

  setMainNumber(selectedIndex: number): void {
    this.phonesArray.controls.forEach((control, index) => {
      control.get('mainPhoneNumber')?.setValue(index === selectedIndex, { emitEvent: false });
    });
  }

  saveSinglePhone(index: number): void {
    const group = this.getGroup(index);
    if (group.invalid) return;
  
    const rawValue = group.getRawValue();
    const targetPersonId = rawValue.id?.personId || this.personId;
  
    if (!targetPersonId) {
      console.error('Speichern abgebrochen: personId ist nicht gesetzt!');
      return;
    }
  
    const countryCode = rawValue.phoneNumber.countryCode?.trim() ?? '';
    const number = rawValue.phoneNumber.phoneNumber?.trim() ?? '';
    const phoneNumberId = rawValue.id?.phoneNumberId 
      ? Number(rawValue.id.phoneNumberId) 
      : (rawValue.phoneNumber?.id 
        ? Number(rawValue.phoneNumber.id) 
        : undefined);
  
    const dto: PersonPhoneNumberDto = {
      id: {
        personId: Number(targetPersonId),
        phoneNumberId: phoneNumberId
      },
      mainPhoneNumber: Boolean(rawValue.mainPhoneNumber),
      phoneNumber: {
        id: phoneNumberId,
        countryCode: countryCode,
        phoneNumber: number,
        fullNumber: `${countryCode}${number}`,
        nationalityId: Number(rawValue.phoneNumber.nationalityId)
      }
    };
  
    this.save.emit(dto);
  }
}