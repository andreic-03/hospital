import { NgModule } from "@angular/core";
import { LoginComponent } from "./components/login/login.component";
import { LoginRoutingModule } from "./login-routing.module";
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ReactiveFormsModule } from "@angular/forms";
import { NotLoggedInHeaderComponent } from './components/not-logged-in-header/not-logged-in-header.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatCardModule,
  ],
})
export class LoginMaterialModule {}

@NgModule({
  declarations: [
    LoginComponent,
    NotLoggedInHeaderComponent,
    SignUpComponent
  ],
  imports: [
    LoginRoutingModule,
    LoginMaterialModule,
    ReactiveFormsModule,
    CommonModule,
  ],
  exports: [
    NotLoggedInHeaderComponent
  ]
})
export class LoginModule {}
