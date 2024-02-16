import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {SignUpComponent} from "./login/components/sign-up/sign-up.component";
import {ConfirmationComponent} from "./login/components/confirmation/confirmation.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'sign-up',
    component: SignUpComponent
  },
  {
    path: 'confirmation',
    component: ConfirmationComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
