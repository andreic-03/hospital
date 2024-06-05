import {NgModule} from "@angular/core";
import {LoginComponent} from "./components/login/login.component";
import {LoginRoutingModule} from "./login-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {SharedMaterialModule, SharedModule} from "../shared/shared.module";
import {TranslateModule} from "@ngx-translate/core";
import {FlexLayoutModule} from "@angular/flex-layout";

@NgModule({
  declarations: [
    LoginComponent,
  ],
  imports: [
    LoginRoutingModule,
    SharedMaterialModule,
    ReactiveFormsModule,
    CommonModule,
    SharedModule,
    TranslateModule,
    FlexLayoutModule,
  ],
  exports: []
})
export class LoginModule {
}
