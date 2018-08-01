export class Dictionary {

   static readonly loginValidationMessage = {
    'username': {
      'required': 'username is required',
      'noUser': 'user is not exists',
      'enableUser': 'user is not active, please active it first'
    },
    'password': {
      'required': 'password is required',
      'wrongPassword': 'password is incorrect',
    }
  };

  static readonly registerValidationMessage = {
    'username': {
      'minlength': 'username at least 3 characters',
      'maxlength': 'username should be less than 30 characters',
      'required': 'username is required',
      'duplicateUser': 'username has been registered, please try another one',
    },
    'password': {
      'minlength': 'password at least 8 characters',
      'maxlength': 'password should be less than 30 characters',
      'required': 'password is required',
      'pattern': 'password is too simple',
    },
    'confPassword': {
      'required': 'please confirm your password',
      'different': 'confirm password is different with the input password',
    },
    'email': {
      'required': 'need an email address to active your Account',
      'pattern': 'not an email account',
      'emailDomain': 'we don\'t support Mail.QQ.com',
    }
  };

  static readonly homeValidationMessage = {
    'searchDate': {
      'required': 'date is required',
      'invalid': 'date is invalid',
    },
  };
}
