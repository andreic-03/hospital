import { Pipe, PipeTransform } from '@angular/core';
import { ValidationErrors } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

@Pipe({
  name: 'formErrorTranslate',
})
export class FormErrorTranslatePipe implements PipeTransform {
  private readonly DEFAULT_ERROR_KEY_PREFIX = 'validation.';

  constructor(private translate: TranslateService) {}

  transform(errors: ValidationErrors | null, ...args: unknown[]): string[] {
    if (errors) {
      return Object.keys(errors).map(key => {
        return this.translate.instant(
          this.DEFAULT_ERROR_KEY_PREFIX + key,
          errors[key]
        );
      });
    } else {
      return [];
    }
  }
}
