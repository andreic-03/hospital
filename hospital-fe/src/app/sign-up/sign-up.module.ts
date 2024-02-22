import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {StepOneComponent} from "./step-one/step-one.component";
import {SignUpRoutingModule} from "./sign-up-routing.module";
import {LoginModule} from "../login/login.module";
import {SharedMaterialModule, SharedModule} from "../shared/shared.module";
import {ReactiveFormsModule} from "@angular/forms";
import { StepTwoComponent } from './step-two/step-two.component';

@NgModule({
  declarations: [
    StepOneComponent,
    StepTwoComponent
  ],
  imports: [
    CommonModule,
    SignUpRoutingModule,
    ReactiveFormsModule,
    LoginModule,
    SharedModule,
    SharedMaterialModule,
  ],
})
export class SignUpModule {}
