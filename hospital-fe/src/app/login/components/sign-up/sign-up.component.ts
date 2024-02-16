import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Store} from "@ngxs/store";
import {Login} from "../../../shared/redux/auth.actions";
import {Navigate} from "@ngxs/router-plugin";
import {RegisterService} from "../../../shared/services/register.service";
import {RegisterStepOneModelRequest, RegisterStepOneModelResponse} from "../../../shared/model/register.model";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {

  user: RegisterStepOneModelRequest = {
    username: '',
    password: '',
    email: '',
    roles: [],
  };
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

  signUp() {
    const username = this.form.get('username')?.value;
    const password = this.form.get('password')?.value;
    const confirmPassword = this.form.get('confirmPassword')?.value;
    const email = this.form.get('email')?.value;

    this.user.username = username;
    this.user.password = password;
    this.user.email = email;
    this.user.roles.push("PATIENT");

    this.registerService.registerStepOne(this.user).subscribe(
      res => {
        this.userResponse = res
      }
    );
  }
}
