import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule } from '@angular/material/dialog';
import {SharedMaterialModule, SharedModule} from "../shared/shared.module";
import { DashboardBaseComponent } from "./components/dashboard-base-component/dashboard-base.component";
import { DashboardComponent } from "./components/dashboard-component/dashboard.component";
import { MedicDashboardComponent } from './components/medic-dashboard-component/medic-dashboard.component';

@NgModule({
  declarations: [],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatDialogModule,
  ],
})
class UserMaterialModule {}

@NgModule({
  declarations: [
    DashboardBaseComponent,
    DashboardComponent,
    MedicDashboardComponent,
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    SharedModule,
    UserMaterialModule,
    ReactiveFormsModule,
    TranslateModule,
    SharedModule,
    SharedMaterialModule,
  ],
})
export class DashboardModule {}
