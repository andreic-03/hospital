import {NgModule} from "@angular/core";
import {AppointmentBaseComponent} from "./components/appointment-base-component/appointment-base.component";
import {AppointmentComponent} from "./components/appointment-component/appointment.component";
import {SharedMaterialModule, SharedModule} from "../shared/shared.module";
import {AppointmentRoutingModule} from "./appointment-routing.module";
import {CommonModule} from "@angular/common";
import { MedicAppointmentComponent } from './components/medic-appointment-component/medic-appointment.component';
import {TranslateModule} from "@ngx-translate/core";
import {ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";
import {MatDialogModule} from "@angular/material/dialog";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";

@NgModule({
  declarations: [],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatCardModule,
    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
})
class AppointmentMaterialModule {}

@NgModule({
  declarations: [
    AppointmentBaseComponent,
    AppointmentComponent,
    MedicAppointmentComponent,
  ],
  imports: [
    SharedModule,
    AppointmentRoutingModule,
    CommonModule,
    TranslateModule,
    SharedMaterialModule,
    ReactiveFormsModule,
    AppointmentMaterialModule,
  ]
})
export class AppointmentModule {}
