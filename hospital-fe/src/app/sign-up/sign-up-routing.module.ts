import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import {StepOneComponent} from "./step-one/step-one.component";
import {StepTwoComponent} from "./step-two/step-two.component";

const routes: Routes = [
  {
    path: 'step-one',
    component: StepOneComponent
  },
  {
    path: 'step-two/:token',
    component: StepTwoComponent
  },
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SignUpRoutingModule {}
