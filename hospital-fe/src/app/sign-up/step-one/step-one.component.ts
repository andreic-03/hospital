import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Store} from "@ngxs/store";
import {RegisterService} from "../../shared/services/register.service";
import {RegisterStepOneModelRequest, RegisterStepOneModelResponse} from "../../shared/model/register.model";
import {Navigate} from "@ngxs/router-plugin";
import {PasswordStrengthValidator} from "./password.validator";
import {createPasswordComparisonValidator} from "../../shared/util/validators.util";
import {catchError, EMPTY} from "rxjs";
import {mapError} from "../../shared/error.util";

@Component({
  selector: 'app-sign-up',
  templateUrl: './step-one.component.html',
  styleUrls: ['./step-one.component.scss']
})
export class StepOneComponent implements OnInit {

  userResponse!: RegisterStepOneModelResponse;

  shouldHidePassword = true;
  shouldHideConfirmPassword = true;

  error = '';

  form: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [
      Validators.required,
      PasswordStrengthValidator,
    ]),
    confirmPassword: new FormControl('', [Validators.required]),
    email: new FormControl('', [
      Validators.required,
      Validators.email]),
    phoneNumber: new FormControl('', [Validators.required])
  });

  constructor(private store: Store,
              private registerService: RegisterService) {
    this.form.addValidators(
      createPasswordComparisonValidator(
        this.form.get('password'),
        this.form.get('confirmPassword')
      )
    )
    this.form.valueChanges.subscribe(() => (this.error = ''));
  }

  ngOnInit(): void {
  }

  signUpStepOne() {
      const user: RegisterStepOneModelRequest = this.form?.value;
      user.roles = ["PATIENT"];

      this.registerService
        .registerStepOne(user)
        .pipe(
          catchError(err => {
            const errResponse = mapError(err);
            this.error = errResponse.message;
            return EMPTY;
          })
        )
        .subscribe(res => {
          if (res) {
            this.userResponse = res;
            this.store.dispatch(new Navigate(['/register/confirmation']));
          }
        }
      );
  }
}
