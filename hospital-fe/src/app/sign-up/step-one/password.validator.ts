import { AbstractControl, ValidationErrors } from '@angular/forms';

export const PasswordStrengthValidator = function (
  control: AbstractControl
): ValidationErrors | null {
  let value: string = control.value || '';

  if (!value) {
    return null;
  }

  let upperCaseCharacters = /[A-Z]+/g;
  if (!upperCaseCharacters.test(value)) {
    return {
      'password-strength.uppercase': true,
    };
  }

  let lowerCaseCharacters = /[a-z]+/g;
  if (!lowerCaseCharacters.test(value)) {
    return {
      'password-strength.lowercase': true,
    };
  }

  let numberCharacters = /[0-9]+/g;
  if (!numberCharacters.test(value)) {
    return {
      'password-strength.numbers': true,
    };
  }

  let specialCharacters = /[!@#$%^&*]+/;
  if (!specialCharacters.test(value)) {
    return {
      'password-strength.special-char': {
        charOptions: '!@#$%^&*',
      },
    };
  }

  if (value.length < 8) {
    return {
      'password-strength.length': {
        minLength: '8',
      },
    };
  }

  return null;
};
