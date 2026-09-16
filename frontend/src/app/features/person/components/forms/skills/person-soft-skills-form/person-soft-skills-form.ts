import { Component, Input, Output, EventEmitter, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { FormListManagerService } from '../../../../form-list-manager';
import { PersonSoftSkillDto } from '../../../../../../types/dto/person/person-skill.dto';
import { PersonSoftSkillDtoId } from '../../../../../../types/dto/person/person-id.dto';

@Component({
  selector: 'app-person-soft-skills-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl, NgbTooltipModule],
  providers: [FormListManagerService],
  templateUrl: './person-soft-skills-form.html',
  styleUrl: './person-soft-skills-form.css'
})
export class PersonSoftSkillsForm implements OnInit {
  private fb = inject(FormBuilder);

  public manager: FormListManagerService<PersonSoftSkillDto> = inject(FormListManagerService);
  public inputTypes = InputTypesEnum;

  public form: FormGroup = this.fb.group({
    softSkills: this.fb.array([])
  });

  @Input({ required: true }) personId!: number | undefined;

  @Input() set softSkills(values: PersonSoftSkillDto[] | undefined) {
    this.ensureManagerInitialized();
    this.manager.syncFromList(values || []);
  }

  @Output() save = new EventEmitter<PersonSoftSkillDto>();
  @Output() delete = new EventEmitter<PersonSoftSkillDtoId>();

  ngOnInit(): void {
    this.ensureManagerInitialized();
  }

  private ensureManagerInitialized(): void {
    if (!this.manager.array) {
      this.manager.init(this.form.get('softSkills') as FormArray, {
        createGroupFn: (item?: Partial<PersonSoftSkillDto>) => this.createSkillGroup(item),
        getIdFn: (group: FormGroup) => {
          const raw = group.getRawValue();
          return raw.softSkill?.id ?? raw.id?.softSkillId ?? null;
        }
      });
    }
  }

  private createSkillGroup(item?: Partial<PersonSoftSkillDto>): FormGroup {
    return this.fb.group({
      id: this.fb.group({
        personId: [item?.id?.personId ?? this.personId, [Validators.required, Validators.min(1)]],
        softSkillId: [item?.id?.softSkillId ?? item?.softSkill?.id ?? null]
      }),
      softSkill: this.fb.group({
        id: [item?.softSkill?.id ?? item?.id?.softSkillId ?? null],
        name: [item?.softSkill?.name ?? '', [Validators.required, Validators.maxLength(250)]],
        description: [item?.softSkill?.description ?? '', [Validators.maxLength(1000)]]
      })
    });
  }

  addSoftSkill(): void {
    if (!this.personId || this.manager.hasUnsavedItem) return;

    const newGroup = this.createSkillGroup();
    this.manager.array.push(newGroup);
    this.manager.registerPristineTracking(newGroup);
  }

  saveSoftSkill(index: number): void {
    if (this.manager.isSaveDisabled(index)) return;

    const isNew = this.manager.isNew(index);
    const group = this.manager.getGroup(index);
    const dto = group.getRawValue() as PersonSoftSkillDto;
    
    if (dto.softSkill) {
      dto.softSkill.name = dto.softSkill.name?.trim() ?? '';
      dto.softSkill.description = dto.softSkill.description?.trim() ?? '';
    }

    if (!isNew) {
      this.manager.markAsSaved(index);
    }

    this.save.emit(dto);
  }

  deleteSoftSkill(index: number): void {
    if (!this.manager.canDelete(index)) return;

    const { id } = this.manager.getGroup(index).getRawValue() as PersonSoftSkillDto;

    id?.personId && id?.softSkillId
      ? this.delete.emit(id)
      : this.manager.array.removeAt(index);
  }
}