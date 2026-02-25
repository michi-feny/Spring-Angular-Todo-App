import { Pipe, PipeTransform, inject } from '@angular/core';
import { ValidationErrors } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { VALIDATION_ERROR_MAPPING, ValidationErrorKey } from './error-messages';

@Pipe({
    name: 'validationError',
    pure: false,
    standalone: true
})
export class ValidationErrorPipe implements PipeTransform {
    private translate = inject(TranslateService);

    transform(errors: ValidationErrors | null | undefined): string {
      if (!errors) return '';

        const firstKey = Object.keys(errors)[0];
        const errorDetails = errors[firstKey];

        if (errorDetails && typeof errorDetails === 'object' && errorDetails.message) {
            return this.translate.instant(errorDetails.message, errorDetails);
        }

        const translationKey = VALIDATION_ERROR_MAPPING[firstKey as ValidationErrorKey];

        if (!translationKey) {
            console.warn(`Keine Übersetzung für Error-Key gefunden: ${firstKey}`);
            return 'ERRORS.DEFAULT'; 
        }

        let details = { ...errorDetails };

        if (firstKey === 'equalsTo' && errorDetails.expected) {
            details.expected = this.translate.instant(details.expected);
        }

        return this.translate.instant(translationKey, details);
    }
}