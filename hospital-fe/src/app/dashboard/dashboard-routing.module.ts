import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardBaseComponent } from "./components/dashboard-base-component/dashboard-base.component";
import { DashboardComponent } from "./components/dashboard-component/dashboard.component";

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
