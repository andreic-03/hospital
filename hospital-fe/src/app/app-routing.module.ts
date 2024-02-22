import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
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
    path: 'register',
    loadChildren: () => import('./sign-up/sign-up.module').then(m => m.SignUpModule),
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
