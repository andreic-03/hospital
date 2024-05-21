import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardBaseComponent } from "./dashboard-base-component/dashboard-base.component";
import { DashboardComponent } from "./dashboard-component/dashboard.component";

const routes: Routes = [
  {
    path: '',
    component: DashboardBaseComponent,
    children: [
      {
        path: '',
        component: DashboardComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule {}
