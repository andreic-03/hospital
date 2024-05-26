import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserProfileComponent} from './components/user-profile-component/user-profile.component';
import {UserProfileRoutingModule} from './user-profile-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatDialogModule} from '@angular/material/dialog';
import {SharedMaterialModule, SharedModule} from "../shared/shared.module";
import {UserProfileBaseComponent} from "./components/user-profile-base-component/user-profile-base.component";
import {MatDividerModule} from "@angular/material/divider";
import {PasswordChangeDialogComponent} from './components/password-change-dialog/password-change-dialog.component';
import { PatientProfilePersonalInfoComponent } from './components/patient-profile-personal-info/patient-profile-personal-info.component';

@NgModule({
  declarations: [],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatDividerModule,
  ],
})
class UserMaterialModule {
}

@NgModule({
  declarations: [
    UserProfileBaseComponent,
    UserProfileComponent,
    PasswordChangeDialogComponent,
    PatientProfilePersonalInfoComponent,
  ],
  imports: [
    CommonModule,
    UserProfileRoutingModule,
    SharedModule,
    UserMaterialModule,
    ReactiveFormsModule,
    TranslateModule,
    SharedModule,
    FormsModule,
    SharedMaterialModule,
  ],
})
export class UserProfileModule {
}
