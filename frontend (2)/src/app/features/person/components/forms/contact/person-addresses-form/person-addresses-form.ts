import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, FormArray, FormControl as AngularFormControl, ReactiveFormsModule, Validators } from '@angular/forms';

import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { PersonAddressDto } from '../../../../../../types/dto/person/related/contact/address/person-address.dto';
import { PersonAddressDtoId } from '../../../../../../types/dto/person/related/reference/contact/person-address-dto-id';

@Component({
  selector: 'app-person-addresses-form',
  standalone: true,
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    AppFormControl
  ],
  templateUrl: './person-addresses-form.html',
  styleUrl: './person-addresses-form.css',
})
export class PersonAddressesForm {
  private fb = inject(FormBuilder);
  public inputTypes = InputTypesEnum;

  @Input({ required: true }) personId!: number | undefined;

  @Input() set addresses(addresses: PersonAddressDto[]) {
    this.setAddressesFormArray(addresses || []);
  }

  @Output() save = new EventEmitter<PersonAddressDto>();
  @Output() delete = new EventEmitter<{ id: PersonAddressDtoId }>();

  public form: FormGroup = this.fb.group({
    addresses: this.fb.array([])
  });

  get addressesArray(): FormArray {
    return this.form.get('addresses') as FormArray;
  }

  getGroup(index: number): FormGroup {
    return this.addressesArray.at(index) as FormGroup;
  }

  getControl(group: AbstractControl, path: string): AngularFormControl {
    return group.get(path) as AngularFormControl;
  }

  private setAddressesFormArray(addresses: PersonAddressDto[]): void {
    const formGroups = addresses.map((item) => this.createAddressGroup(item));
    this.form.setControl('addresses', this.fb.array(formGroups));
  }

  private createAddressGroup(item?: Partial<PersonAddressDto>): FormGroup {
    return this.fb.group({
      id: this.fb.group({
        personId: [item?.id?.personId ?? this.personId, [Validators.required, Validators.min(1)]],
        addressId: [item?.id?.addressId ?? item?.address?.id ?? null]
      }),
      mainAddress: [item?.mainAddress ?? false, Validators.required],
      address: this.fb.group({
        id: [item?.address?.id ?? item?.id?.addressId ?? null],
        street: [item?.address?.street ?? '', Validators.required],
        houseNumber: [item?.address?.houseNumber ?? '', Validators.required],
        zipCode: [item?.address?.zipCode ?? '', Validators.required],
        city: [item?.address?.city ?? '', Validators.required],
        nationalityId: [item?.address?.nationalityId ?? 1, [Validators.required, Validators.min(1)]]
      })
    });
  }

  addAddress(): void {
    if (!this.personId) return;

    this.addressesArray.push(
      this.createAddressGroup({
        id: {
          personId: this.personId,
          addressId: undefined
        },
        mainAddress: this.addressesArray.length === 0,
        address: {
          id: undefined,
          street: '',
          houseNumber: '',
          zipCode: '',
          city: '',
          nationalityId: 1 // Standardmäßig z.B. Österreich (ID 1)
        }
      })
    );
  }

  deleteAddress(index: number): void {
    const group = this.getGroup(index);
    const rawValue = group.getRawValue();

    const personId = Number(rawValue.id?.personId || this.personId);
    const addressId = rawValue.id?.addressId ? Number(rawValue.id.addressId) : (rawValue.address?.id ? Number(rawValue.address.id) : undefined);

    if (personId && addressId) {
      this.delete.emit({
        id: {
          personId,
          addressId
        }
      });
    } else {
      this.addressesArray.removeAt(index);
    }
  }

  setMainAddress(selectedIndex: number): void {
    this.addressesArray.controls.forEach((control, index) => {
      control.get('mainAddress')?.setValue(index === selectedIndex, { emitEvent: false });
    });
  }

  saveSingleAddress(index: number): void {
    const group = this.getGroup(index);
    if (group.invalid) return;

    const rawValue = group.getRawValue();
    const targetPersonId = rawValue.id?.personId || this.personId;

    if (!targetPersonId) {
      console.error('Speichern abgebrochen: personId ist nicht gesetzt!');
      return;
    }

    const addressId = rawValue.id?.addressId ? Number(rawValue.id.addressId) : (rawValue.address?.id ? Number(rawValue.address.id) : undefined);

    const dto: PersonAddressDto = {
      id: {
        personId: Number(targetPersonId),
        addressId: addressId
      },
      mainAddress: Boolean(rawValue.mainAddress),
      address: {
        id: addressId,
        street: rawValue.address.street?.trim() ?? '',
        houseNumber: rawValue.address.houseNumber?.trim() ?? '',
        zipCode: rawValue.address.zipCode?.trim() ?? '',
        city: rawValue.address.city?.trim() ?? '',
        nationalityId: Number(rawValue.address.nationalityId)
      }
    };

    this.save.emit(dto);
  }
}