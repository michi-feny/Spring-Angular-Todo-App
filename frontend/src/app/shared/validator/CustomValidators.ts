import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export interface ChoiceValidatorOptions {
    choices: any[] | object;
    multiple?: boolean;
}

export class CustomValidators {
    static passwordStrength(minlength: number = 10): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value;
            if (!value) return null;

            const hasNumber = /[0-9]/.test(value);
            const hasUpper = /[A-Z]/.test(value);
            const hasLower = /[a-z]/.test(value);
            const hasSpecial = /[@#$%^&+=!]/.test(value);
            const noWhitespace = /^\S+$/.test(value);
            const isValidLength = value.length >= minlength;

            const passwordValid = hasNumber && hasUpper && hasLower && hasSpecial && noWhitespace && isValidLength;

            return !passwordValid ? {
                passwordStrength: {
                    number: !hasNumber,
                    upper: !hasUpper,
                    lower: !hasLower,
                    special: !hasSpecial,
                    whitespace: !noWhitespace,
                    length: !isValidLength
                }
            } : null;
        };
    }

    static equalsTo(firstField: string, secondField: string, targetLabelKey: string): ValidatorFn {
        return (group: AbstractControl): ValidationErrors | null => {
            const first = group.get(firstField);
            const second = group.get(secondField);
    
            if (!first || !second) {
                throw new Error(`equalsTo: One of the controls (${firstField}, ${secondField}) was not found.`);
            }
    
            const isMatch = first.value === second.value;
    
            if (!isMatch && second.value !== '') {
                second.setErrors({ 
                    ...second.errors, 
                    equalsTo: { expected: targetLabelKey } 
                });
            } else {
                if (second.errors?.['equalsTo']) {
                    const { equalsTo, ...rest } = second.errors;
                    const remainingErrors = Object.keys(rest).length > 0 ? rest : null;
                    second.setErrors(remainingErrors);
                }
            }
    
            return null;
        };
    }

    static choice(options: ChoiceValidatorOptions): ValidatorFn {
        const isMultiple = options.multiple ?? false;
        let allowedValues: any[];

        if (Array.isArray(options.choices)) {
            allowedValues = options.choices;
        } else if (typeof options.choices === 'object' && options.choices !== null) {
            allowedValues = Object.values(options.choices);
        } else {
            allowedValues = [options.choices];
        }

        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value;
            
            if (value === null || value === undefined || value === '') return null;

            const checkValue = (val: any) => allowedValues.includes(val);

            if (isMultiple) {
                if (!Array.isArray(value)) {
                    throw new Error('If multiple is set to true the provided value to check must be an array');
                }

                const allValid = value.every(val => checkValue(val));
                return allValid ? null : { choice: { allowedValues } };
            }

            return checkValue(value) ? null : { choice: {  allowedValues } };
        };
    }
}