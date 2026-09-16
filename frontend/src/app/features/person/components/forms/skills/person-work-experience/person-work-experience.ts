import { Component, EventEmitter, inject, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormListManagerService } from '../../../../form-list-manager';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators  } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { NgbModalOptions, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { MergeWorkExperiencesRequestDto, PersonWorkExperienceDto } from '../../../../../../types/dto/person/person-skill.dto';
import { PersonWorkExperienceDtoId } from '../../../../../../types/dto/person/person-id.dto';
import { Modal } from '../../../../../../shared/components/bootstrap/modal/modal';
import { WorkExperienceDto } from '../../../../../../types/dto/common/skill.dto';
@Component({
  selector: 'app-person-work-experience',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl, NgbTooltipModule, Modal],
  providers: [FormListManagerService],
  templateUrl: './person-work-experience.html',
  styleUrl: './person-work-experience.css',
})
export class PersonWorkExperience implements OnInit {
  private fb = inject(FormBuilder);
  
  public manager: FormListManagerService<PersonWorkExperienceDto> = inject(FormListManagerService);
  public inputTypes = InputTypesEnum;

  public selectedIndices = new Set<number>();
  public mergeForm!: FormGroup;

  public expandedHistoryMap = new Map<number, boolean>();

  @ViewChild('mergeModal') mergeModal!: Modal;

  public form: FormGroup = this.fb.group({
    workExperiences: this.fb.array([])
  });

  @Input({ required: true }) personId!: number | undefined | null;

  @Input() set workExperiences(values: PersonWorkExperienceDto[]) {
    this.ensureManagerInitialized();
    this.manager.syncFromList(values || []);
    this.selectedIndices.clear();
  }

  @Output() save = new EventEmitter<PersonWorkExperienceDto>();
  @Output() delete = new EventEmitter<PersonWorkExperienceDtoId>();
  @Output() merge = new EventEmitter<MergeWorkExperiencesRequestDto>();

  ngOnInit(): void {
    this.ensureManagerInitialized();
  }

  toggleHistory(masterWorkExpId: number): void {
    const isExpanded = !!this.expandedHistoryMap.get(masterWorkExpId);
    this.expandedHistoryMap.set(masterWorkExpId, !isExpanded);
  }

  isHistoryExpanded(masterWorkExpId: number): boolean {
    return !!this.expandedHistoryMap.get(masterWorkExpId);
  }

  getMergedSubRecords(masterWorkExpId: number | null | undefined): AbstractControl[] {
    if (!masterWorkExpId || !this.manager.array) return [];

    return this.manager.array.controls.filter((ctrl) => {
      const val = ctrl.value;
      return !val.visible && val.mergedIntoWorkExpId === masterWorkExpId;
    });
  }

  private ensureManagerInitialized(): void {
    if (!this.manager.array) {
      this.manager.init(this.form.get('workExperiences') as FormArray, {
        createGroupFn: (item?: Partial<PersonWorkExperienceDto>) => this.createWorkExperienceGroup(item),
        getIdFn: (group: FormGroup) => {
          const raw = group.getRawValue();
          return raw.workExperience?.id ?? raw.id?.workExperienceId ?? null;
        }
      });
    }
  }

  private createWorkExperienceGroup(item?: Partial<PersonWorkExperienceDto>): FormGroup {
    return this.fb.group({
      id: this.fb.group({
        personId: [item?.id?.personId ?? this.personId, [Validators.required, Validators.min(1)]],
        workExperienceId: [item?.id?.workExperienceId ?? item?.workExperience?.id ?? null]
      }),
      displayOrder: [item?.displayOrder ?? 0, [Validators.required, Validators.min(0)]],
      visible: [item?.visible ?? true, [Validators.required]],
      mergedIntoWorkExpId: [item?.mergedIntoWorkExpId ?? null],
      workExperience: this.fb.group({
        id: [item?.workExperience?.id ?? null],
        startDate: [item?.workExperience?.startDate ?? '', [Validators.required]],
        endDate: [item?.workExperience?.endDate ?? ''],
        jobTitle: [item?.workExperience?.jobTitle ?? '', [Validators.required, Validators.maxLength(100)]],
        description: [item?.workExperience?.description ?? ''],
        militaryService: [item?.workExperience?.militaryService ?? false, [Validators.required]],
        company: this.fb.group({
          id: [item?.workExperience?.company?.id ?? null],
          name: [item?.workExperience?.company?.name ?? '', [Validators.required, Validators.maxLength(100)]],
          legalForm: [item?.workExperience?.company?.legalForm ?? ''],
          address: this.fb.group({
            id: [item?.workExperience?.company?.address?.id ?? null],
            street: [item?.workExperience?.company?.address?.street ?? '', [Validators.required]],
            houseNumber: [item?.workExperience?.company?.address?.houseNumber ?? '', [Validators.required]],
            zipCode: [item?.workExperience?.company?.address?.zipCode ?? '', [Validators.required]],
            city: [item?.workExperience?.company?.address?.city ?? '', [Validators.required]],
            nationalityId: [item?.workExperience?.company?.address?.nationalityId ?? 1]
          })
        })
      })
    });
  }

  private createWorkExperienceOnlyGroup(): FormGroup {
    return this.fb.group({
      id: [null],
      startDate: [''],
      endDate: [''],
      jobTitle: ['', [Validators.required, Validators.maxLength(100)]],
      description: ['', [Validators.maxLength(1000)]],
      militaryService: [false],
      company: this.fb.group({
        id: [null],
        name: ['DUMMY'],
        legalForm: ['DUMMY'],
        address: this.fb.group({
          id: [null],
          street: ['DUMMY'],
          houseNumber: ['DUMMY'],
          zipCode: ['DUMMY'],
          city: ['DUMMY'],
          nationalityId: [1]
        })
      })
    });
  }

  toggleSelection(index: number, event: Event): void {
    const isChecked = (event.target as HTMLInputElement).checked;
    if (isChecked) {
      this.selectedIndices.add(index);
    } else {
      this.selectedIndices.delete(index);
    }
  }

  isSelected(index: number): boolean {
    return this.selectedIndices.has(index);
  }

  get canMerge(): boolean {
    return this.selectedIndices.size >= 2;
  }

  openMergeModal(): void {
    if (!this.canMerge) return;
  
    const selectedRecords = Array.from(this.selectedIndices)
      .map(index => this.manager.getGroup(index).getRawValue() as PersonWorkExperienceDto)
      .filter(record => record.workExperience);

    selectedRecords.sort((a, b) => {
      const dateA = a.workExperience?.startDate ? new Date(a.workExperience.startDate).getTime() : 0;
      const dateB = b.workExperience?.startDate ? new Date(b.workExperience.startDate).getTime() : 0;
      return dateA - dateB;
    });
    
    const earliestStartDate = selectedRecords[0]?.workExperience?.startDate ?? '';

    const hasUnfinishedJob = selectedRecords.some(r => !r.workExperience?.endDate);
    let latestEndDate = '';

    if (!hasUnfinishedJob) {
      const sortedByEnd = [...selectedRecords].sort((a, b) => {
        const dateA = a.workExperience?.endDate ? new Date(a.workExperience.endDate).getTime() : 0;
        const dateB = b.workExperience?.endDate ? new Date(b.workExperience.endDate).getTime() : 0;
        return dateB - dateA;
      });
      latestEndDate = sortedByEnd[0]?.workExperience?.endDate ?? '';
    }

    this.mergeForm = this.createWorkExperienceOnlyGroup();

    this.mergeForm.patchValue({
      startDate: earliestStartDate,
      endDate: latestEndDate
    });

    const modalOptions: NgbModalOptions = { size: 'lg', backdrop: 'static' };
    this.mergeModal.openModal(modalOptions);
  }

  submitMerge(): void {
    console.log(this.mergeForm.invalid);
    if (this.mergeForm.invalid) {
      this.mergeForm.markAllAsTouched();
      return;
    }

    const selectedSourceIds: number[] = Array.from(this.selectedIndices)
      .map(index => {
        const raw = this.manager.getGroup(index).getRawValue();
        return raw.workExperience?.id ?? raw.id?.workExperienceId;
      })
      .filter((id): id is number => id !== null && id !== undefined);
    
    const mergedDto = this.mergeForm.getRawValue() as WorkExperienceDto;
  
    this.merge.emit({
      personId: this.personId!,
      workExpIdsToMerge: selectedSourceIds,
      newMasterDetails: mergedDto
    });
    
    this.mergeModal.closeModal('Submit Merge');
    this.selectedIndices.clear();
  }

  addWorkExperience(): void {
    if (!this.personId || this.manager.hasUnsavedItem) return;

    const newGroup = this.createWorkExperienceGroup();
    this.manager.array.push(newGroup);
    this.manager.registerPristineTracking(newGroup);
  }

  saveWorkExperience(index: number): void {
    if (this.manager.isSaveDisabled(index)) return;
  
    const isNew = this.manager.isNew(index);
    const group = this.manager.getGroup(index);
    const dto = group.getRawValue() as PersonWorkExperienceDto;
    console.log(isNew);
    if (!isNew) {
      this.manager.markAsSaved(index);
    }
  
    this.save.emit(dto);
  }

  deleteWorkExperience(index: number): void {
    if (!this.manager.canDelete(index)) return;

    const { id } = this.manager.getGroup(index).getRawValue() as PersonWorkExperienceDto;

    id?.personId && id?.workExperienceId
      ? this.delete.emit(id)
      : this.manager.array.removeAt(index);
  }
}