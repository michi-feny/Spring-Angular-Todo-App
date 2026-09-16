import { Component, Input, Output, EventEmitter, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, FormArray, FormControl as AngularFormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { FormListManagerService } from '../../../../form-list-manager';
import { PersonEmailAddressDto } from '../../../../../../types/dto/person/person-contact.dto';
import { PersonEmailAddressDtoId } from '../../../../../../types/dto/person/person-id.dto';

@Component({
  selector: 'app-person-emails-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl, NgbTooltipModule],
  providers: [FormListManagerService],
  templateUrl: './person-emails-form.html'
})
export class PersonEmailsForm implements OnInit {
  private fb = inject(FormBuilder);

  public manager: FormListManagerService<PersonEmailAddressDto> = inject(FormListManagerService);
  public inputTypes = InputTypesEnum;

  public form: FormGroup = this.fb.group({
    emails: this.fb.array([])
  });

  @Input({ required: true }) personId!: number | undefined;

  @Input() set emails(values: PersonEmailAddressDto[] | undefined) {
    this.ensureManagerInitialized();
    // Nutzt die generische Sync-Logik des Managers
    this.manager.syncFromList(values || []);
  }

  @Output() save = new EventEmitter<PersonEmailAddressDto>();
  @Output() delete = new EventEmitter<PersonEmailAddressDtoId>();

  ngOnInit(): void {
    this.ensureManagerInitialized();
  }

  private ensureManagerInitialized(): void {
    if (!this.manager.array) {
      this.manager.init(this.form.get('emails') as FormArray, {
        createGroupFn: (item?: Partial<PersonEmailAddressDto>) => this.createEmailGroup(item),
        getIdFn: (group: FormGroup) => {
          const raw = group.getRawValue();
          return raw.emailAddress?.id ?? raw.id?.emailAddressId ?? null;
        },
        mainControlPath: 'mainEmail'
      });
    }
  }

  private createEmailGroup(item?: Partial<PersonEmailAddressDto>): FormGroup {
    return this.fb.group({
      id: this.fb.group({
        personId: [item?.id?.personId ?? this.personId, [Validators.required, Validators.min(1)]],
        emailAddressId: [item?.id?.emailAddressId ?? item?.emailAddress?.id ?? null]
      }),
      mainEmail: [item?.mainEmail ?? false, Validators.required],
      emailAddress: this.fb.group({
        id: [item?.emailAddress?.id ?? item?.id?.emailAddressId ?? null],
        emailAddress: [item?.emailAddress?.emailAddress ?? '', [Validators.required, Validators.email]]
      })
    });
  }

  addEmail(): void {
    if (!this.personId || this.manager.hasUnsavedItem) return;

    const isFirst = !this.manager.hasSavedMain() && this.manager.array.length === 0;
    const newGroup = this.createEmailGroup({ mainEmail: isFirst });

    this.manager.array.push(newGroup);
    this.manager.registerPristineTracking(newGroup);
  }

  saveSingleEmail(index: number): void {
    if (this.manager.isSaveDisabled(index)) return;

    const isNew = this.manager.isNew(index);
    const group = this.manager.getGroup(index);
    const dto = group.getRawValue() as PersonEmailAddressDto;

    dto.emailAddress.emailAddress = dto.emailAddress.emailAddress?.trim() ?? '';

    if (!isNew) {
      this.manager.markAsSaved(index);
    }

    this.save.emit(dto);
  }

  deleteEmail(index: number): void {
    if (!this.manager.canDelete(index)) return;

    const { id } = this.manager.getGroup(index).getRawValue() as PersonEmailAddressDto;

    id?.personId && id?.emailAddressId
      ? this.delete.emit(id)
      : this.manager.array.removeAt(index);
  }
}