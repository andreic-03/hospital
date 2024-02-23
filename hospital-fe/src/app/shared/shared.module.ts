import {NgModule} from "@angular/core";
import {NotLoggedInHeaderComponent} from "./components/not-logged-in-header/not-logged-in-header.component";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatCardModule} from "@angular/material/card";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import { HeaderComponent } from './components/header/header.component';
import { HeaderUserComponent } from './components/header/header-user/header-user.component';
import {MatMenuModule} from "@angular/material/menu";
import { ErrorMessageComponent } from './components/error-message/error-message.component';

@NgModule({
  declarations: [],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatMenuModule,
  ],
})
export class SharedMaterialModule {}

@NgModule({
  declarations: [
    NotLoggedInHeaderComponent,
    HeaderComponent,
    HeaderUserComponent,
    ErrorMessageComponent
  ],
  exports: [
    NotLoggedInHeaderComponent,
    HeaderComponent,
    HeaderUserComponent,
    ErrorMessageComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedMaterialModule,
  ],
})
export class SharedModule {}
