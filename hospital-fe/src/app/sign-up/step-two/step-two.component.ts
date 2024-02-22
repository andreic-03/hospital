import {Component, OnInit} from '@angular/core';
import {
  RegisterStepTwoModelRequest
} from "../../shared/model/register.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Store} from "@ngxs/store";
import {RegisterService} from "../../shared/services/register.service";
import {Navigate} from "@ngxs/router-plugin";
import {PatientResponseModel} from "../../shared/model/patient.mode";
import {ActivatedRoute, ParamMap} from "@angular/router";

@Component({
  selector: 'app-step-two',
  templateUrl: './step-two.component.html',
  styleUrls: ['./step-two.component.scss']
})
export class StepTwoComponent implements OnInit {
  patientResponse!: PatientResponseModel;

  token!: string;

  form: FormGroup = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    cnp: new FormControl('', [Validators.required]),
    age: new FormControl('', [Validators.required]),
    gender: new FormControl('', [Validators.required]),
    mobilePhone: new FormControl('', [Validators.required]),
  });

  constructor(private store: Store,
              private route: ActivatedRoute,
              private registerService: RegisterService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      if (!params.has('token')) {
        this.store.dispatch(new Navigate(["/"]));
      } else {
        this.token = params.get('token') || '';
      }
    });
  }

  signUpStepTwo() {
    const user: RegisterStepTwoModelRequest = this.form?.value;
    user.token = this.token;

    this.registerService.registerStepTwo(user).subscribe(
      res => {
        if (res) {
          this.patientResponse = res;
          this.store.dispatch(new Navigate(['/login']));
        }
      }
    );
  }
}
