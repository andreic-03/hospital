import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Store} from "@ngxs/store";
import {RegisterService} from "../../shared/services/register.service";
import {RegisterStepOneModelRequest, RegisterStepOneModelResponse} from "../../shared/model/register.model";
import {Router} from "@angular/router";
import {Navigate} from "@ngxs/router-plugin";

@Component({
  selector: 'app-sign-up',
  templateUrl: './step-one.component.html',
  styleUrls: ['./step-one.component.scss']
})
export class StepOneComponent implements OnInit {

  userResponse!: RegisterStepOneModelResponse;

  shouldHidePassword = true;

  form: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
    confirmPassword: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required]),
  });

  constructor(private store: Store,
              private registerService: RegisterService) { }

  ngOnInit(): void {
  }

  signUpStepOne() {
      const user: RegisterStepOneModelRequest = this.form?.value;
      user.roles = ["PATIENT"];

      this.registerService.registerStepOne(user).subscribe(
        res => {
          if (res) {
            this.userResponse = res;
            this.store.dispatch(new Navigate(['/confirmation']));
          }
        }
      );
  }
}
