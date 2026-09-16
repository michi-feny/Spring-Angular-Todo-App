import { Component, Input, Output, EventEmitter, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {  FormBuilder, FormGroup, FormArray, FormControl as AngularFormControl, ReactiveFormsModule, Validators } from '@angular/forms';

import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { selectAllCountries } from '../../../../../../store/country/county.selectors';
import { Store } from '@ngrx/store';
import { loadCountries } from '../../../../../../store/country/county.actions';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormListManagerService } from '../../../../form-list-manager';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { PersonAddressDto } from '../../../../../../types/dto/person/person-contact.dto';
import { PersonAddressDtoId } from '../../../../../../types/dto/person/person-id.dto';

const DEFAULT_AUSTRIA_NATIONALITY_ID = 1;

@Component({
  selector: 'app-person-addresses-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl, NgSelectModule, NgbTooltipModule],
  providers: [FormListManagerService],
  templateUrl: './person-addresses-form.html',
  styleUrl: './person-addresses-form.css',
})
export class PersonAddressesForm implements OnInit {
  private store = inject(Store);
  private fb = inject(FormBuilder);

  public manager: FormListManagerService<PersonAddressDto> = inject(FormListManagerService);

  public inputTypes = InputTypesEnum;
  public countries = this.store.selectSignal(selectAllCountries);

  public form: FormGroup = this.fb.group({ addresses: this.fb.array([]) });

  @Input({ required: true }) personId!: number | undefined;

  @Input() set addresses(values: PersonAddressDto[]) {
    this.ensureManagerInitialized();
    this.manager.syncFromList(values || []);
  }

  @Output() save = new EventEmitter<PersonAddressDto>();
  @Output() delete = new EventEmitter<PersonAddressDtoId>();

  ngOnInit(): void {
    this.ensureManagerInitialized();
  }

  private ensureManagerInitialized(): void {
    if (!this.manager.array) {
      this.manager.init(this.form.get('addresses') as FormArray, {
        createGroupFn: (item?: Partial<PersonAddressDto>) => this.createAddressGroup(item),
        getIdFn: (group: FormGroup) => {
          const raw = group.getRawValue();
          return raw.address?.id ?? raw.id?.addressId ?? null;
        },
        mainControlPath: 'mainAddress'
      });
    }
  }

  private createAddressGroup(item?: Partial<PersonAddressDto>): FormGroup {
    const addressId = item?.id?.addressId ?? item?.address?.id ?? null;

    return this.fb.group({
      id: this.fb.group({
        personId: [item?.id?.personId ?? this.personId, [Validators.required, Validators.min(1)]],
        addressId: [addressId]
      }),
      mainAddress: [item?.mainAddress ?? false, Validators.required],
      address: this.fb.group({
        id: [addressId],
        street: [item?.address?.street ?? '', Validators.required],
        houseNumber: [item?.address?.houseNumber ?? '', Validators.required],
        zipCode: [item?.address?.zipCode ?? '', Validators.required],
        city: [item?.address?.city ?? '', Validators.required],
        nationalityId: [item?.address?.nationalityId ?? DEFAULT_AUSTRIA_NATIONALITY_ID, [Validators.required, Validators.min(1)]]
      })
    });
  }

  addAddress(): void {
    if (!this.personId || this.manager.hasUnsavedItem) return;

    const isFirst = !this.manager.hasSavedMain() && this.manager.array.length === 0;
    const newGroup = this.createAddressGroup({ mainAddress: isFirst });

    this.manager.array.push(newGroup);
    this.manager.registerPristineTracking(newGroup);
  }

  saveSingleAddress(index: number): void {
    if (this.manager.isSaveDisabled(index)) return;

    const group = this.manager.getGroup(index);
    const dto = group.getRawValue() as PersonAddressDto;
    const isNew = !(dto.address?.id || dto.id?.addressId);

    dto.address.street = dto.address.street?.trim() ?? '';
    dto.address.houseNumber = dto.address.houseNumber?.trim() ?? '';
    dto.address.zipCode = dto.address.zipCode?.trim() ?? '';
    dto.address.city = dto.address.city?.trim() ?? '';

    if (!isNew) {
      this.manager.markAsSaved(index);
    }

    this.save.emit(dto);
  }

  deleteAddress(index: number): void {
    if (!this.manager.canDelete(index)) return;

    const { id } = this.manager.getGroup(index).getRawValue() as PersonAddressDto;

    id?.personId && id?.addressId
      ? this.delete.emit(id)
      : this.manager.array.removeAt(index);
  }
}