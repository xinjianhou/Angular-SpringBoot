import {FormControl, FormGroup} from '@angular/forms';

export class ValidationUtil {

  static onValueChanged(form: FormGroup, formErrors: any, validationMessage: any, data?: any): void {

    for (const field of Object.keys(formErrors)) {

      formErrors[field] = '';
      const control = form.get(field);

      if (control && (control.dirty || !data) && control.invalid) {
        const messages = validationMessage[field];
        for (const key of Object.keys(control.errors)) {
          formErrors[field] += messages[key];
        }
      }
    }

  }
  static emailDomainValidator(control: FormControl): any {
    const email = control.value;
    if (email && email.indexOf('@') !== -1) {
      const [_, domain] = email.split('@');
      if (domain.toLowerCase() === ('qq.com')) {
        return {
          emailDomain: {
            parsedDomain: domain
          }
        };
      }
    }
    return null;
  }

}
