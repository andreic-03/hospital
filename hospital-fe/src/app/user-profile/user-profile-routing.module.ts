import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserProfileComponent } from './components/user-profile-component/user-profile.component';
import { UserProfileBaseComponent } from "./components/user-profile-base-component/user-profile-base.component";

const routes: Routes = [
  {
    path: '',
    component: UserProfileBaseComponent,
    children: [
      {
        path: '',
        component: UserProfileComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserProfileRoutingModule {}
