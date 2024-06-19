import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AppointmentBaseComponent} from "./components/appointment-base-component/appointment-base.component";
import {AppointmentComponent} from "./components/appointment-component/appointment.component";

const routes: Routes = [
  {
    path: '',
    component: AppointmentBaseComponent,
    children: [
      {
        path: '',
        component: AppointmentComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AppointmentRoutingModule {}
