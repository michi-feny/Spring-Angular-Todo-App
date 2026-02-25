import { FormGroup } from '@angular/forms';

export class FormHelper {
    static setFormErrors(form: FormGroup, backendErrors: Record<string, string>): void {
        Object.keys(backendErrors).forEach(key => {
            const control = form.get(key);
            if (control) {
                control.setErrors({
                    serverError: { message: backendErrors[key] }
                });
                control.markAsTouched();
            }
        });
    }
}