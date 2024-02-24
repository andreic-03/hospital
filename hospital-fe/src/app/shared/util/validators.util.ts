import { AbstractControl } from '@angular/forms';

export function createPasswordComparisonValidator(
  controlOne: AbstractControl | null,
  controlTwo: AbstractControl | null
) {
  return () => {
    if (controlOne?.value !== controlTwo?.value)
      return { 'password-match': true };
    return null;
  };
}
