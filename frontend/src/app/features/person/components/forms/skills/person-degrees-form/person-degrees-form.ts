import { Component, Input, Output, EventEmitter, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { 
  ReactiveFormsModule, 
  FormGroup, 
  FormArray, 
  FormBuilder, 
  Validators, 
  AbstractControl, 
  FormControl as AngularFormControl 
} from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { PersonDegreeDto } from '../../../../../../types/dto/person/person-skill.dto';
import { PersonDegreeDtoId } from '../../../../../../types/dto/person/person-id.dto';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { FormListManagerService } from '../../../../form-list-manager';

@Component({
  selector: 'app-person-degrees-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl, NgbTooltipModule],
  providers: [FormListManagerService],
  templateUrl: './person-degrees-form.html',
  styleUrl: './person-degrees-form.css',
})
export class PersonDegreesForm implements OnInit {
  private fb = inject(FormBuilder);

  public manager: FormListManagerService<PersonDegreeDto> = inject(FormListManagerService);
  public inputTypes = InputTypesEnum;

  public form: FormGroup = this.fb.group({
    degrees: this.fb.array([])
  });

  @Input({ required: true }) personId!: number | undefined;

  @Input() set degrees(values: PersonDegreeDto[] | undefined) {
    this.ensureManagerInitialized();
    this.manager.syncFromList(values || []);
  }

  @Output() save = new EventEmitter<PersonDegreeDto>();
  @Output() delete = new EventEmitter<PersonDegreeDtoId>();

  ngOnInit(): void {
    this.ensureManagerInitialized();
  }

  private ensureManagerInitialized(): void {
    if (!this.manager.array) {
      this.manager.init(this.form.get('degrees') as FormArray, {
        createGroupFn: (item?: Partial<PersonDegreeDto>) => this.createDegreeGroup(item),
        getIdFn: (group: FormGroup) => {
          const raw = group.getRawValue();
          return raw.degree?.id ?? raw.id?.degreeId ?? null;
        }
      });
    }
  }

  private createDegreeGroup(item?: Partial<PersonDegreeDto>): FormGroup {
    const personIdVal = item?.id?.personId ?? this.personId;
    const degreeIdVal = item?.id?.degreeId ?? item?.degree?.id ?? null;
    const instIdVal = item?.id?.educationInstitutionId ?? item?.educationInstitution?.id ?? null;

    return this.fb.group({
      id: this.fb.group({
        personId: [personIdVal, [Validators.required, Validators.min(1)]],
        degreeId: [degreeIdVal],
        educationInstitutionId: [instIdVal]
      }),
      progressInPercent: [item?.progressInPercent ?? 100, [Validators.required, Validators.min(0), Validators.max(100)]],
      degreeDuration: this.fb.group({
        startDate: [item?.degreeDuration?.startDate ?? '', [Validators.required]],
        endDate: [item?.degreeDuration?.endDate ?? null]
      }),
      degree: this.fb.group({
        id: [degreeIdVal],
        name: [item?.degree?.name ?? '', [Validators.required, Validators.maxLength(500)]],
        level: [item?.degree?.level ?? '', [Validators.required, Validators.maxLength(100)]],
        skillType: [item?.degree?.skillType ?? 'DEGREE'],
        weight: [item?.degree?.weight ?? 0, [Validators.required, Validators.min(0)]],
        preName: [item?.degree?.preName ?? false, [Validators.required]],
        postName: [item?.degree?.postName ?? false, [Validators.required]]
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

  addDegree(): void {
    if (!this.personId || this.manager.hasUnsavedItem) return;

    const newGroup = this.createDegreeGroup();
    this.manager.array.push(newGroup);
    this.manager.registerPristineTracking(newGroup);
  }

  saveDegree(index: number): void {
    if (this.manager.isSaveDisabled(index)) return;

    const isNew = this.manager.isNew(index);
    const group = this.manager.getGroup(index);
    const dto = group.getRawValue() as PersonDegreeDto;

    if (!isNew) {
      this.manager.markAsSaved(index);
    }

    this.save.emit(dto);
  }

  deleteDegree(index: number): void {
    if (!this.manager.canDelete(index)) return;

    const { id } = this.manager.getGroup(index).getRawValue() as PersonDegreeDto;

    id?.personId && (id?.degreeId || id?.educationInstitutionId)
      ? this.delete.emit(id)
      : this.manager.array.removeAt(index);
  }
}