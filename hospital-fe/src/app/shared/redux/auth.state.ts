import { Injectable } from "@angular/core";
import { User } from "../model/user.mode";
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { Login } from "./auth.actions";
import { AuthService } from "../services/auth.service";
import { tap, switchMap } from 'rxjs';

export class AuthStateModel {
  accessToken?: string;
  user?: User;
}

@State<AuthStateModel>({
  name: 'Auth',
  defaults: {},
})
@Injectable()
export class AuthState {
  constructor(private authService: AuthService) {}

  @Action(Login)
  login({ patchState }: StateContext<AuthStateModel>, login: Login) {
    return this.authService
      .login({
        username: login.username,
        password: login.password,
      })
      .pipe(
        tap(response => {
          patchState({
            accessToken: response.jwtToken,
          });
          AuthState.saveAccessToken(response.jwtToken);
        }),
        switchMap(() => this.authService.getCurrentUserInfo(login.username)),
        tap(response => {
          patchState({
            user: response,
          });
        })
      );
  }

  private static saveAccessToken(accessToken: string): void {
    localStorage.setItem('accessToken', accessToken);
  }
}

