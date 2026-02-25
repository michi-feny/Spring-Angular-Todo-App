import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, FormControl as AngularFormControl, ReactiveFormsModule } from '@angular/forms';
import { InputTypesEnum } from '../../../../types/InputTypesEnum';
import { ValidationErrorPipe } from '../../../validator/ValidationErrorPipe';
import { TranslateModule } from '@ngx-translate/core';

@Component({
    selector: 'app-form-control',
    imports: [CommonModule, ReactiveFormsModule, ValidationErrorPipe, TranslateModule],
    templateUrl: './form-control.html',
    styleUrl: './form-control.css',
})
export class FormControl implements OnInit {
    @Input({ required: true }) control!: AbstractControl;
    @Input() label: string = '';
    @Input() type: InputTypesEnum = InputTypesEnum.Text;
    @Input() placeholder: string = ''

    @Input() multiple: boolean = false;
    @Input() options: { value: any; label: string }[] = [];

    @Input() customErrorMessage: string|null = null;

    ngOnInit(): void {
        if (this.multiple && this.type !== InputTypesEnum.Select && this.type !== InputTypesEnum.Checkbox) {
            this.multiple = false;
        }
    }

    onCheckboxChange(checked: boolean, value: any): void {
        if (!this.multiple || this.type !== 'checkbox') return;

        let selectedValues: any[] = (this.control.value as any[] || []) as any[];

        if (checked) {
            if (!selectedValues.includes(value)) {
                selectedValues = [...selectedValues, value];
            }
        } else {
            selectedValues = selectedValues.filter(v => v !== value);
        }

        (this.control as any).setValue(selectedValues);
        this.control.markAsDirty();
  }

    get fieldId(): string {

        let id = this.label.toLowerCase().trim();

        id = id.replace(/[\s:]+/g, '-');
        id = id.replace(/[^a-z0-9-]/g, '');

        if (!id) return `control-${Math.random().toString(36).substring(2, 9)}`;
        return id;
    }
}
