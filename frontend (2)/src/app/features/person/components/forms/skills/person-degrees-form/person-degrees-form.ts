import { Component, EventEmitter, inject, Input, Output, SimpleChanges } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl as AngularFormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { PersonDegree } from '../../../../../../types/person';
import { CommonModule } from '@angular/common';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';

@Component({
  selector: 'app-person-degrees-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl],
  templateUrl: './person-degrees-form.html',
  styleUrl: './person-degrees-form.css',
})
export class PersonDegreesForm {
  private fb = inject(FormBuilder);
  public inputTypes = InputTypesEnum;

  @Input() degrees: PersonDegree[] = [];
  @Output() save = new EventEmitter<PersonDegree[]>();

  public form: FormGroup = this.fb.group({
    degreesArray: this.fb.array([])
  });

  get degreesArray(): FormArray {
    return this.form.get('degreesArray') as FormArray;
  }

  getControl(group: AbstractControl, path: string): AngularFormControl {
    return group.get(path) as AngularFormControl;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['degrees']) {
      this.populateForm();
    }
  }

  private populateForm(): void {
    this.degreesArray.clear();
    this.degrees?.forEach(pd => {
      this.degreesArray.push(this.fb.group({
        startDate: [pd.startDate || ''],
        endDate: [pd.endDate || ''],
        progressInPercent: [pd.progressInPercent || 0],
        degree: this.fb.group({
          name: [pd.degree?.name || ''],
          level: [pd.degree?.level || '']
        }),
        institution: this.fb.group({
          name: [pd.institution?.name || '']
        })
      }));
    });
  }

  addDegree(): void {
    this.degreesArray.push(this.fb.group({
      startDate: [''],
      endDate: [''],
      progressInPercent: [0],
      degree: this.fb.group({ name: [''], level: [''] }),
      institution: this.fb.group({ name: [''] })
    }));
  }

  removeDegree(index: number): void {
    this.degreesArray.removeAt(index);
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.save.emit(this.degreesArray.value);
    }
  }
}