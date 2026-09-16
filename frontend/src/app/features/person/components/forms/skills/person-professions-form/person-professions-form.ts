import { Component, Input, Output, EventEmitter, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators, AbstractControl, FormControl as AngularFormControl } from '@angular/forms';

import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { PersonProfessionQualificationDto } from '../../../../../../types/dto/person/person-skill.dto';
import { PersonProfessionQualificationDtoId } from '../../../../../../types/dto/person/person-id.dto';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { FormListManagerService } from '../../../../form-list-manager';

@Component({
  selector: 'app-person-professions-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl, NgbTooltipModule],
  providers: [FormListManagerService],
  templateUrl: './person-professions-form.html',
  styleUrl: './person-professions-form.css',
})
export class PersonProfessionsForm implements OnInit {
  private fb = inject(FormBuilder);

  public manager: FormListManagerService<PersonProfessionQualificationDto> = inject(FormListManagerService);
  public inputTypes = InputTypesEnum;

  public form: FormGroup = this.fb.group({
    professions: this.fb.array([])
  });

  @Input({ required: true }) personId!: number | undefined;

  @Input() set professions(values: PersonProfessionQualificationDto[]) {
    this.ensureManagerInitialized();
    this.manager.syncFromList(values);
  }

  @Output() save = new EventEmitter<PersonProfessionQualificationDto>();
  @Output() delete = new EventEmitter<PersonProfessionQualificationDtoId>();

  ngOnInit(): void {
    this.ensureManagerInitialized();
  }

  private ensureManagerInitialized(): void {
    if (!this.manager.array) {
      this.manager.init(this.form.get('professions') as FormArray, {
        createGroupFn: (item?: Partial<PersonProfessionQualificationDto>) => this.createProfessionGroup(item),
        getIdFn: (group: FormGroup) => {
          const raw = group.getRawValue();
          return raw.id?.professionQualificationId ?? raw.professionQualification?.id ?? null;
        }
      });
    }
  }

  private createProfessionGroup(item?: Partial<PersonProfessionQualificationDto>): FormGroup {
    const personIdVal = item?.id?.personId ?? this.personId;
    const profIdVal = item?.id?.professionQualificationId ?? item?.professionQualification?.id ?? null;
    const instIdVal = item?.id?.educationInstitutionId ?? item?.educationInstitution?.id ?? null;
    return this.fb.group({
      id: this.fb.group({
        personId: [personIdVal, [Validators.required, Validators.min(1)]],
        professionQualificationId: [profIdVal],
        educationInstitutionId: [instIdVal]
      }),
      certificateNumber: [item?.certificateNumber ?? ''],
      professionQualificationDuration: this.fb.group({
        startDate: [item?.professionQualificationDuration?.startDate ?? '', [Validators.required]],
        endDate: [item?.professionQualificationDuration?.endDate ?? null]
      }),
      professionQualification: this.fb.group({
        id: [profIdVal],
        name: [item?.professionQualification?.name ?? '', [Validators.required, Validators.maxLength(500)]],
        level: [item?.professionQualification?.level ?? ''],
        skillType: [item?.professionQualification?.skillType ?? 'PROFESSION_QUALIFICATION'],
        weight: [item?.professionQualification?.weight ?? 1]
      }),
      educationInstitution: this.fb.group({
        id: [instIdVal],
        name: [item?.educationInstitution?.name ?? '', [Validators.required, Validators.maxLength(200)]],
        address: this.fb.group({
          id: [item?.educationInstitution?.address?.id ?? null],
          street: [item?.educationInstitution?.address?.street ?? '', [Validators.required]],
          houseNumber: [item?.educationInstitution?.address?.houseNumber ?? '', [Validators.required]],
          zipCode: [item?.educationInstitution?.address?.zipCode ?? '', [Validators.required]],
          city: [item?.educationInstitution?.address?.city ?? '', [Validators.required]],
          nationalityId: [item?.educationInstitution?.address?.nationalityId ?? 1, [Validators.required]]
        })
      })
    });
  }

  addProfession(): void {
    if (!this.personId || this.manager.hasUnsavedItem) return;

    const newGroup = this.createProfessionGroup();
    this.manager.array.push(newGroup);
    this.manager.registerPristineTracking(newGroup);
  }

  saveProfession(index: number): void {
    if (this.manager.isSaveDisabled(index)) return;

    const isNew = this.manager.isNew(index);
    const group = this.manager.getGroup(index);
    const dto = group.getRawValue() as PersonProfessionQualificationDto;

    if (!isNew) {
      this.manager.markAsSaved(index);
    }

    this.save.emit(dto);
  }

  deleteProfession(index: number): void {
    if (!this.manager.canDelete(index)) return;

    const { id } = this.manager.getGroup(index).getRawValue() as PersonProfessionQualificationDto;

    id?.personId && (id?.professionQualificationId || id?.educationInstitutionId)
      ? this.delete.emit(id)
      : this.manager.array.removeAt(index);
  }
}