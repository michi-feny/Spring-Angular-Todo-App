export const VALIDATION_ERROR_MAPPING = {
    required: 'ERRORS.REQUIRED',
    email: 'ERRORS.EMAIL',
    min: 'ERRORS.MIN',
    max: 'ERRORS.MAX',
    minlength: 'ERRORS.MINLENGTH',
    maxlength: 'ERRORS.MAXLENGTH',
    pattern: 'ERRORS.PATTERN',
    passwordStrength: 'ERRORS.PASSWORD_STRENGTH',
    equalsTo: 'ERRORS.EQUALS_TO',
    choice: 'ERRORS.CHOICE'
} as const;

export type ValidationErrorKey = keyof typeof VALIDATION_ERROR_MAPPING;