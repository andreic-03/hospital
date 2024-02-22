import {NgModule} from "@angular/core";
import {NotLoggedInHeaderComponent} from "./components/not-logged-in-header/not-logged-in-header.component";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatCardModule} from "@angular/material/card";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";

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
export class SharedMaterialModule {}

@NgModule({
  declarations: [
    NotLoggedInHeaderComponent,
  ],
  exports: [
    NotLoggedInHeaderComponent,
  ],
    imports: [
        CommonModule,
        RouterModule
    ],
})
export class SharedModule {}
