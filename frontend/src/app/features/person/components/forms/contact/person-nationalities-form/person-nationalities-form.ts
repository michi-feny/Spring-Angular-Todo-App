import { CommonModule } from '@angular/common';
import { Component, effect, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl as AngularFormControl, FormGroup, ReactiveFormsModule, Validators, NgSelectOption } from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { Store } from '@ngrx/store';
import { selectAllCountries } from '../../../../../../store/country/county.selectors';
import { loadCountries } from '../../../../../../store/country/county.actions';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormListManagerService } from '../../../../form-list-manager';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { PersonCountryDto } from '../../../../../../types/dto/person/person-contact.dto';
import { PersonCountryDtoId } from '../../../../../../types/dto/person/person-id.dto';

@Component({
  selector: 'app-person-nationalities-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgSelectModule, NgbTooltipModule],
  providers: [FormListManagerService],
  templateUrl: './person-nationalities-form.html'
})
export class PersonNationalitiesForm implements OnInit {
  private store = inject(Store);
  private fb = inject(FormBuilder);

  public manager: FormListManagerService<PersonCountryDto> = inject(FormListManagerService);
  public countries = this.store.selectSignal(selectAllCountries);

  public form: FormGroup = this.fb.group({
    nationalities: this.fb.array([])
  });

  @Input({ required: true }) personId!: number | undefined;

  @Input() set nationalities(values: PersonCountryDto[] | undefined) {
    this.ensureManagerInitialized();

    this.manager.syncFromList(values || [], (group, item) => {
      const raw = group.getRawValue();
      const matchesOriginal = raw._originalCountryId === item.id?.countryId;
      const matchesCurrent = raw.id?.countryId === item.id?.countryId;
      return raw.id?.personId === item.id?.personId && (matchesOriginal || matchesCurrent);
    });
  }
  @Output() save = new EventEmitter<{ data: PersonCountryDto; isNew: boolean }>();
  @Output() delete = new EventEmitter<PersonCountryDtoId>();

  ngOnInit(): void {
    this.ensureManagerInitialized();
  }

  private ensureManagerInitialized(): void {
    if (!this.manager.array) {
      this.manager.init(this.form.get('nationalities') as FormArray, {
        createGroupFn: (item?: Partial<PersonCountryDto>) => {
          const isPersisted = !!(item?.id?.countryId && item?.id?.personId);
          return this.createNationalityGroup(item, isPersisted);
        },
        getIdFn: (group: FormGroup) => {
          const raw = group.getRawValue();
          return raw._isPersisted ? (raw._originalCountryId ?? raw.id?.countryId ?? null) : null;
        },
        mainControlPath: 'mainCountry'
      });
    }
  }

  private createNationalityGroup(item?: Partial<PersonCountryDto>, isPersisted: boolean = false): FormGroup {
    const countryId = item?.id?.countryId ?? null;
    return this.fb.group({
      _isPersisted: [isPersisted],
      _originalCountryId: [isPersisted ? countryId : null],
      id: this.fb.group({
        personId: [item?.id?.personId ?? this.personId, [Validators.required, Validators.min(1)]],
        countryId: [countryId, [Validators.required, Validators.min(1)]]
      }),
      mainCountry: [item?.mainCountry ?? false, Validators.required]
    });
  }

  addNationality(): void {
    if (!this.personId || this.manager.hasUnsavedItem) return;

    const isFirst = !this.manager.hasSavedMain() && this.manager.array.length === 0;
    const newGroup = this.createNationalityGroup({ mainCountry: isFirst }, false);

    this.manager.array.push(newGroup);
    this.manager.registerPristineTracking(newGroup);
  }

  saveNationality(index: number): void {
    if (this.manager.isSaveDisabled(index)) return;

    const group = this.manager.getGroup(index);
    const raw = group.getRawValue();
    const wasPersisted = raw._isPersisted === true;

    group.get('_isPersisted')?.setValue(true, { emitEvent: false });
    group.get('_originalCountryId')?.setValue(raw.id?.countryId, { emitEvent: false });

    if (this.manager.isSaved(index)) {
      this.manager.markAsSaved(index);
    }

    const dto: PersonCountryDto = {
      id: raw.id,
      mainCountry: raw.mainCountry
    };

    this.save.emit({
      data: dto,
      isNew: !wasPersisted
    });
  }

  deleteNationality(index: number): void {
    if (!this.manager.canDelete(index)) return;

    const group = this.manager.getGroup(index);
    const raw = group.getRawValue();

    raw._isPersisted && raw.id?.personId && raw.id?.countryId
      ? this.delete.emit(raw.id)
      : this.manager.array.removeAt(index);
  }
}