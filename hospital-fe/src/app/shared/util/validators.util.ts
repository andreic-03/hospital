import {AbstractControl, ValidationErrors} from '@angular/forms';

export function createPasswordComparisonValidator(
  controlOne: AbstractControl | null,
  controlTwo: AbstractControl | null
) {
  return () => {
    if (controlOne?.value !== controlTwo?.value)
      return {'password-match': true};
    return null;
  };
}

export const validateCNP = function (
  control: AbstractControl
): ValidationErrors | null {
  let value: string = control.value || '';

  if (!value) {
    return null;
  }

  let firstDigit = parseInt(value.substring(0, 1), 10);
  if (firstDigit > 6) {
    return {'cnp-validation.first-digit': true}
  }

  let month = parseInt(value.substring(3, 5), 10);
  if (month < 1 || month > 12) {
    return {'cnp-validation.month': true}
  }

  let day = parseInt(value.substring(5, 7), 10);
  if (day < 1 || (day > 29 && month === 2)
    || (day > 30 && [4, 6, 9, 11].includes(month))
    ||(day > 31 && [1, 3, 5, 7, 8, 10, 12])) {

    return {'cnp-validation.day': true}
  }

  if (value.length != 13) {
    return {'cnp-validation.length': true}
  }

  return null;
}
