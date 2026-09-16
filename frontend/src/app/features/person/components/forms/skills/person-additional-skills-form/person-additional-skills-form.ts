import { Component, Input, Output, EventEmitter, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators, AbstractControl, FormControl as AngularFormControl } from '@angular/forms';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { PersonAdditionalHardSkillDto } from '../../../../../../types/dto/person/person-skill.dto';
import { PersonAdditionalHardSkillDtoId } from '../../../../../../types/dto/person/person-id.dto';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { FormListManagerService } from '../../../../form-list-manager';

@Component({
  selector: 'app-person-additional-skills-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl, NgbTooltipModule],
  providers: [FormListManagerService],
  templateUrl: './person-additional-skills-form.html',
  styleUrl: './person-additional-skills-form.css',
})
export class PersonAdditionalSkillsForm implements OnInit {
  private fb = inject(FormBuilder);

  public manager: FormListManagerService<PersonAdditionalHardSkillDto> = inject(FormListManagerService);
  public inputTypes = InputTypesEnum;

  public form: FormGroup = this.fb.group({
    additionalSkills: this.fb.array([])
  });

  @Input({ required: true }) personId!: number | undefined;

  @Input() set additionalSkills(values: PersonAdditionalHardSkillDto[]) {
    this.ensureManagerInitialized();
    this.manager.syncFromList(values);
  }

  @Output() save = new EventEmitter<PersonAdditionalHardSkillDto>();
  @Output() delete = new EventEmitter<PersonAdditionalHardSkillDtoId>();

  ngOnInit(): void {
    this.ensureManagerInitialized();
  }

  private ensureManagerInitialized(): void {
    if (!this.manager.array) {
      this.manager.init(this.form.get('additionalSkills') as FormArray, {
        createGroupFn: (item?: Partial<PersonAdditionalHardSkillDto>) => this.createSkillGroup(item),
        getIdFn: (group: FormGroup) => {
          const raw = group.getRawValue();
          return raw.personAdditionalHardSkillDto?.id ?? raw.id?.additionalHardSkillId ?? null;
        }
      });
    }
  }

  private createSkillGroup(item?: Partial<PersonAdditionalHardSkillDto>): FormGroup {
    const skill = item?.personAdditionalHardSkillDto;
    const personIdVal = item?.id?.personId ?? this.personId;
    const skillIdVal = item?.id?.additionalHardSkillId ?? skill?.id ?? null;

    return this.fb.group({
      id: this.fb.group({
        personId: [personIdVal, [Validators.required, Validators.min(1)]],
        additionalHardSkillId: [skillIdVal]
      }),
      personAdditionalHardSkillDto: this.fb.group({
        id: [skillIdVal],
        name: [skill?.name ?? '', [Validators.required, Validators.maxLength(500)]],
        level: [skill?.level ?? null],
        skillType: [skill?.skillType ?? 'ADDITIONAL_HARD_SKILL']
      })
    });
  }

  addSkill(): void {
    if (!this.personId || this.manager.hasUnsavedItem) return;

    const newGroup = this.createSkillGroup();
    this.manager.array.push(newGroup);
    this.manager.registerPristineTracking(newGroup);
  }

  saveSkill(index: number): void {
    if (this.manager.isSaveDisabled(index)) return;

    const isNew = this.manager.isNew(index);
    const group = this.manager.getGroup(index);
    const dto = group.getRawValue() as PersonAdditionalHardSkillDto;

    if (dto.personAdditionalHardSkillDto) {
      dto.personAdditionalHardSkillDto.name = dto.personAdditionalHardSkillDto.name?.trim() || '';
      dto.personAdditionalHardSkillDto.level = dto.personAdditionalHardSkillDto.level?.trim() || null;
    }

    if (!isNew) {
      this.manager.markAsSaved(index);
    }

    this.save.emit(dto);
  }

  deleteSkill(index: number): void {
    if (!this.manager.canDelete(index)) return;

    const { id } = this.manager.getGroup(index).getRawValue() as PersonAdditionalHardSkillDto;

    id?.personId && id?.additionalHardSkillId
      ? this.delete.emit(id)
      : this.manager.array.removeAt(index);
  }
}