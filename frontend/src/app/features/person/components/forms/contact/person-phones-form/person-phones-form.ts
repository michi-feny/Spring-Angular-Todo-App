import { Component, Input, Output, EventEmitter, inject, OnInit, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, FormArray, FormControl as AngularFormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { getCountries, getCountryCallingCode } from 'libphonenumber-js';
import { NgSelectModule } from '@ng-select/ng-select';
import { CustomValidators } from '../../../../../../shared/validator/CustomValidators';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { FormListManagerService } from '../../../../form-list-manager';
import { Store } from '@ngrx/store';
import { selectAllCountries } from '../../../../../../store/country/county.selectors';
import { loadCountries } from '../../../../../../store/country/county.actions';
import { PersonPhoneNumberDto } from '../../../../../../types/dto/person/person-contact.dto';
import { PersonPhoneNumberDtoId } from '../../../../../../types/dto/person/person-id.dto';


@Component({
  selector: 'app-person-phones-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl, NgSelectModule, NgbTooltipModule],
  providers: [FormListManagerService],
  templateUrl: './person-phones-form.html'
})
export class PersonPhonesForm implements OnInit {
  private store = inject(Store);
  private fb = inject(FormBuilder);

  public manager: FormListManagerService<PersonPhoneNumberDto> = inject(FormListManagerService);
  public inputTypes = InputTypesEnum;
  public countries = this.store.selectSignal(selectAllCountries);

  @Input({ required: true }) personId!: number | undefined;

  @Input() set phones(values: PersonPhoneNumberDto[] | undefined) {
    this.ensureManagerInitialized();
    this.manager.syncFromList(values || []);
  }

  @Output() save = new EventEmitter<PersonPhoneNumberDto>();
  @Output() delete = new EventEmitter<PersonPhoneNumberDtoId>();

  private regionNames = new Intl.DisplayNames(['de'], { type: 'region' });

  public countryPhoneList = getCountries().map(countryCode => {
    const phoneCode = `+${getCountryCallingCode(countryCode)}`;
    const name = this.regionNames.of(countryCode) || countryCode;

    return {
      iso: countryCode,
      name: name,
      phoneCode: phoneCode,
      label: `${name} (${phoneCode})`
    };
  });

  public form: FormGroup = this.fb.group({
    phones: this.fb.array([])
  });

  constructor() {
    effect(() => {
      const countryList = this.countries();
      if (countryList.length > 0 && this.manager?.array) {
        this.manager.array.controls.forEach(control => {
          const phoneGroup = control.get('phoneNumber') as FormGroup;
          const currentCode = phoneGroup?.get('countryCode')?.value;
          if (currentCode) {
            this.syncCountryId(phoneGroup, currentCode);
          }
        });
      }
    });
  }

  ngOnInit(): void {
    this.ensureManagerInitialized();

    if (this.countries().length === 0) {
      this.store.dispatch(loadCountries());
    }
  }

  private ensureManagerInitialized(): void {
    if (!this.manager.array) {
      this.manager.init(this.form.get('phones') as FormArray, {
        createGroupFn: (item?: Partial<PersonPhoneNumberDto>) => this.createPhoneGroup(item),
        getIdFn: (group: FormGroup) => {
          const raw = group.getRawValue();
          return raw.phoneNumber?.id ?? raw.id?.phoneNumberId ?? null;
        },
        mainControlPath: 'mainPhoneNumber'
      });
    }
  }

  phoneCodeSearch(term: string, item: any): boolean {
    term = term.toLowerCase();
    const nameMatch = item.name?.toLowerCase().includes(term);
    const codeMatch = item.phoneCode?.toLowerCase().includes(term);
    const isoMatch = item.iso?.toLowerCase().includes(term);

    return nameMatch || codeMatch || isoMatch;
  }

  private createPhoneGroup(item?: Partial<PersonPhoneNumberDto>): FormGroup {
    const group = this.fb.group({
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
        nationalityId: [item?.phoneNumber?.nationalityId ?? null, [Validators.required, Validators.min(1)]],
      }, {
        validators: [CustomValidators.phoneNumber('countryCode', 'phoneNumber')]
      })
    });

    const phoneSubGroup = group.get('phoneNumber') as FormGroup;
    const countryCodeControl = phoneSubGroup.get('countryCode');

    countryCodeControl?.valueChanges.subscribe((code: string) => {
      this.syncCountryId(phoneSubGroup, code);
    });

    if (countryCodeControl?.value) {
      this.syncCountryId(phoneSubGroup, countryCodeControl.value);
    }

    return group;
  }

  private syncCountryId(phoneNumberGroup: FormGroup, phoneCode: string): void {
    if (!phoneCode) return;

    const foundPhoneItem = this.countryPhoneList.find(item => item.phoneCode === phoneCode);
    if (!foundPhoneItem) return;

    const storeCountries = this.countries();
    if (!storeCountries || storeCountries.length === 0) return;

    const targetIso = foundPhoneItem.iso.toLowerCase();

    const matchedCountry = storeCountries.find((c: any) => {
      const iso = (c.isoCode || c.code || c.iso || c.iso2 || c.alpha2Code || '').toLowerCase();
      return iso === targetIso;
    });

    if (matchedCountry?.id) {
      phoneNumberGroup.get('nationalityId')?.setValue(matchedCountry.id, { emitEvent: false });
    }
  }

  addPhone(): void {
    if (!this.personId || this.manager.hasUnsavedItem) return;

    const isFirst = !this.manager.hasSavedMain() && this.manager.array.length === 0;
    const newGroup = this.createPhoneGroup({ mainPhoneNumber: isFirst });

    this.manager.array.push(newGroup);
    this.manager.registerPristineTracking(newGroup);
  }

  savePhone(index: number): void {
    if (this.manager.isSaveDisabled(index)) return;

    const isNew = this.manager.isNew(index);
    const group = this.manager.getGroup(index);
    const dto = group.getRawValue() as PersonPhoneNumberDto;

    dto.phoneNumber.countryCode = dto.phoneNumber.countryCode?.trim() ?? '';
    dto.phoneNumber.phoneNumber = dto.phoneNumber.phoneNumber?.trim() ?? '';
    dto.phoneNumber.fullNumber = `${dto.phoneNumber.countryCode}${dto.phoneNumber.phoneNumber}`;

    if (!isNew) {
      this.manager.markAsSaved(index);
    }

    this.save.emit(dto);
  }

  deletePhone(index: number): void {
    if (!this.manager.canDelete(index)) return;

    const { id } = this.manager.getGroup(index).getRawValue() as PersonPhoneNumberDto;

    id?.personId && id?.phoneNumberId
      ? this.delete.emit(id)
      : this.manager.array.removeAt(index);
  }
}