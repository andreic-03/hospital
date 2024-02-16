import { Injectable } from "@angular/core";
import { User } from "../model/user.model";
import { Action, Selector, State, StateContext } from '@ngxs/store';
import {GetCurrentUserInfo, Login} from "./auth.actions";
import { AuthService } from "../services/auth.service";
import {tap, switchMap, EMPTY} from 'rxjs';

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

  @Selector()
  static getAccessToken(state: AuthStateModel): string | undefined {
    const savedAccessToken = AuthState.loadAccessToken();
    return state.accessToken || savedAccessToken;
  }

  @Selector()
  static getCurrentUserInfo(state: AuthStateModel): User | undefined {
    return state.user;
  }

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
            accessToken: response.accessToken,
          });
          AuthState.saveAccessToken(response.accessToken);
        }),
        switchMap(() => this.authService.getCurrentUserInfo()),
        tap(response => {
          patchState({
            user: response,
          });
        })
      );
  }

  @Action(GetCurrentUserInfo)
  getCurrentUserInfo({ getState, patchState }: StateContext<AuthStateModel>) {
    if (!AuthState.getAccessToken(getState())) {
      return EMPTY;
    } else {
      return this.authService.getCurrentUserInfo().pipe(
        tap(response => {
          patchState({
            user: response,
          });
        })
      );
    }
  }

  private static saveAccessToken(accessToken: string): void {
    localStorage.setItem('accessToken', accessToken);
  }

  private static loadAccessToken(): string | undefined {
    return localStorage.getItem('accessToken') || undefined;
  }
  private static clearAccessToken(): void {
    localStorage.removeItem('accessToken');
  }
}

