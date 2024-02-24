import { NgModule } from "@angular/core";
import { LoginComponent } from "./components/login/login.component";
import { LoginRoutingModule } from "./login-routing.module";
import { ReactiveFormsModule } from "@angular/forms";
import {CommonModule} from "@angular/common";
import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import {SharedMaterialModule, SharedModule} from "../shared/shared.module";
import {TranslateModule} from "@ngx-translate/core";

@NgModule({
  declarations: [
    LoginComponent,
    ConfirmationComponent
  ],
    imports: [
        LoginRoutingModule,
        SharedMaterialModule,
        ReactiveFormsModule,
        CommonModule,
        SharedModule,
        TranslateModule,
    ],
  exports: [
  ]
})
export class LoginModule {}
