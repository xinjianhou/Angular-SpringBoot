import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbTooltipConfig} from '@ng-bootstrap/ng-bootstrap';
import {Dictionary, ValidationUtil} from '../_utils';
import {User} from '../_models';
import {AuthenticationService} from '../_services';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [NgbTooltipConfig]
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;

  formErrors = {
    username: '',
    password: ''
  };

  user: User;

  constructor(private router: Router, private formBuilder: FormBuilder,
              private config: NgbTooltipConfig, private authenticationService: AuthenticationService) {
    config.placement = 'right';
    config.triggers = 'click:hover';
  }

  ngOnInit() {
    this.buildForm();
    // reset login status
    this.authenticationService.logout();
  }

  get f() {
    return this.loginForm.controls;
  }

  buildForm(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],

    });
    this.loginForm.valueChanges
      .subscribe(data => ValidationUtil.onValueChanged(this.loginForm, this.formErrors, Dictionary.loginValidationMessage, data));

    ValidationUtil.onValueChanged(this.loginForm, this.formErrors, Dictionary.loginValidationMessage);
  }

  checkUsername(value: string): void {
    if (this.f.username.invalid) {
      return;
    }
    this.authenticationService.checkByUsername(value).subscribe(user => {
      if (!user) {
        this.f.username.setErrors({
          noUser: {
            parsedDomain: value,
          }
        });

      } else if (!user.enabled) {
        this.f.username.setErrors({
          enableUser: {
            parsedDomain: value,
          }
        });
      }
      ValidationUtil.onValueChanged(this.loginForm, this.formErrors, Dictionary.loginValidationMessage);
    });
  }

  onSubmit(): void {

    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;

    this.authenticationService.login(new User(this.f.username.value, this.f.password.value)).subscribe(
      result => {
        if (result) {
          // login successful
          this.router.navigate(['home']);
        } else {
          // login failed
          console.log('password is incorrect');
          this.f.password.setErrors({
            wrongPassword: {
              parsedDomain: result,
            }
          });
          ValidationUtil.onValueChanged(this.loginForm, this.formErrors, Dictionary.loginValidationMessage);
          this.loading = false;
        }
      }
    );
  }
}
