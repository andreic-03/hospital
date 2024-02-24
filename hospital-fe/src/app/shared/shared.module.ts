import {NgModule} from "@angular/core";
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import {NotLoggedInHeaderComponent} from "./components/not-logged-in-header/not-logged-in-header.component";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatCardModule} from "@angular/material/card";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {HeaderComponent} from './components/header/header.component';
import {HeaderUserComponent} from './components/header/header-user/header-user.component';
import {MatMenuModule} from "@angular/material/menu";
import {ErrorMessageComponent} from './components/error-message/error-message.component';
import {FormErrorTranslatePipe} from "./pipes/form-error-translate.pipe";
import {HttpClient} from "@angular/common/http";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";

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
export class SharedMaterialModule {
}

@NgModule({
  declarations: [
    NotLoggedInHeaderComponent,
    HeaderComponent,
    HeaderUserComponent,
    ErrorMessageComponent,
    FormErrorTranslatePipe,
  ],
  exports: [
    NotLoggedInHeaderComponent,
    HeaderComponent,
    HeaderUserComponent,
    ErrorMessageComponent,
    FormErrorTranslatePipe,
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedMaterialModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: (http: HttpClient) => new TranslateHttpLoader(http),
        deps: [HttpClient],
      },
    }),
  ],
})
export class SharedModule {
}
