import { Pipe, PipeTransform } from '@angular/core';
import { FormControl } from '@angular/forms';

@Pipe({
  name: 'control',
  standalone: true
})
export class FormControlPipe implements PipeTransform {
    transform(formGroup: any, controlName: string): FormControl {
        const control = formGroup.get(controlName);

        if (!control) {
            throw new Error(`Control mit dem Namen ${controlName} wurde in der FormGroup nicht gefunden.`);
        }

        return control as FormControl;
    }
}