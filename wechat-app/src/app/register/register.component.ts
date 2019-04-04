import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbTooltipConfig } from '@ng-bootstrap/ng-bootstrap';
import { Dictionary, ValidationUtil } from '../_utils';
import { UserModel } from '../_models';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from '../alert/alert.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  providers: [NgbTooltipConfig]
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  errorMessage: string;

  constructor(private router: Router, private formBuilder: FormBuilder, @Inject('userService') private userService,
              @Inject('authService') private authenticationService, /* config: NgbTooltipConfig */private modalService: NgbModal) {
    // config.placement = 'bottom';
    //  config.triggers = 'input:hover';
  }

  formErrors = {
    username: '',
    password: '',
    confPassword: '',
    email: '',
  };
  loading = false;

  ngOnInit() {
    this.buildForm();
  }

  get f() {
    return this.registerForm.controls;
  }

  openAlert(head: string, boday: string): void {
    const modalRef = this.modalService.open(AlertComponent);
    modalRef.componentInstance.headContent = 'World';
  }

  buildForm(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30),
        Validators.pattern('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}')]],
      confPassword: ['', [Validators.required]],
      email: ['', [Validators.required,
        Validators.pattern('^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$'),
        ValidationUtil.emailDomainValidator]],
    });
    this.registerForm.valueChanges
      .subscribe(data => ValidationUtil.onValueChanged(this.registerForm, this.formErrors, Dictionary.registerValidationMessage, data));
    ValidationUtil.onValueChanged(this.registerForm, this.formErrors, Dictionary.registerValidationMessage);
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      return;
    }
    this.loading = true;
    this.userService.register(new UserModel(this.f.username.value, this.f.password.value, this.f.email.value)).subscribe(
      result => {
        if (result) {
          this.openAlert('Register Success', `please open <a>${this.f.email.value}</a> and click the link to active your account.`);
          // this.router.navigate(['/registerSuccess'],{ skipLocationChange: true });
        } else {
          this.errorMessage = 'Register failed, please try again later!';
          this.loading = false;
        }
      }
    );

  }

  confPasswordValidation(value: string): void {
    const confPassword = value;
    if (confPassword && this.f.password.value) {
      if (confPassword !== this.f.password.value) {
        this.f.confPassword.setErrors({
          different: {
            parsedDomain: value
          }
        });
        ValidationUtil.onValueChanged(this.registerForm, this.formErrors, Dictionary.registerValidationMessage);
      }
    }
  }

  checkUsername(value: string): void {
    if (this.f.username.invalid) {
      return;
    }
    this.authenticationService.checkByUsername(value).subscribe(user => {
      if (user) {
        this.f.username.setErrors({
          duplicateUser: {
            parsedDomain: value,
          }
        });
        ValidationUtil.onValueChanged(this.registerForm, this.formErrors, Dictionary.registerValidationMessage);
      }
    });
    console.log(this.registerForm);
  }

}
