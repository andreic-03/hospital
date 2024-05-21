import { Injectable } from "@angular/core";
import { User } from "../model/user.model";
import { Action, Selector, State, StateContext } from '@ngxs/store';
import {GetCurrentUserInfo, GetMedicInfo, GetPatientInfo, Login, Logout} from "./auth.actions";
import { AuthService } from "../services/auth.service";
import {tap, switchMap, EMPTY, filter} from 'rxjs';
import {PatientResponseModel} from "../model/patient.model";
import {MedicResponseModel} from "../model/medic.model";
import {MedicService} from "../services/medic.service";
import {PatientService} from "../services/patient.service";

export class AuthStateModel {
  accessToken?: string;
  user?: User;
  medic?: MedicResponseModel;
  patient?: PatientResponseModel;
}

@State<AuthStateModel>({
  name: 'Auth',
  defaults: {},
})
@Injectable()
export class AuthState {
  constructor(private authService: AuthService,
              private medicService: MedicService,
              private patientService: PatientService) {}

  @Selector()
  static getAccessToken(state: AuthStateModel): string | undefined {
    const savedAccessToken = AuthState.loadAccessToken();
    return state.accessToken || savedAccessToken;
  }

  @Selector()
  static getCurrentUserInfo(state: AuthStateModel): User | undefined {
    return state.user;
  }

  @Selector()
  static getMedicInfo(state: AuthStateModel): MedicResponseModel | undefined {
    return state.medic;
  }

  @Selector()
  static getPatientInfo(state: AuthStateModel): PatientResponseModel | undefined {
    return state.patient;
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

  @Action(Logout)
  logout({ patchState }: StateContext<AuthStateModel>) {
    return this.authService.logout().pipe(
      tap(() => {
        patchState({
          accessToken: undefined,
          user: undefined,
        });
        AuthState.clearAccessToken();
      })
    );
  }

  @Action(GetCurrentUserInfo)
  getCurrentUserInfo({ getState, patchState }: StateContext<AuthStateModel>) {
    if (!AuthState.getAccessToken(getState())) {
      return EMPTY;
    } else {
      return this.authService.getCurrentUserInfo().pipe(
        filter((res) =>
          JSON.stringify(getState().user) !== JSON.stringify(res),
        ),
        tap((res) => {
          return patchState({
            user: res,
          });
        })
      );
    }
  }

  @Action(GetMedicInfo)
  getMedicInfo({ getState, patchState}: StateContext<AuthStateModel>) {
    const user = getState().user;
    if (user == null) {
      return EMPTY;
    } else {
      return this.medicService.findById(user.medicId).pipe(
        filter((res) =>
          JSON.stringify(getState().medic) !== JSON.stringify(res),
        ),
        tap((res) => {
          return patchState({
            medic: res,
          })
        })
      );
    }
  }

  @Action(GetPatientInfo)
  getPatientInfo({ getState, patchState}: StateContext<AuthStateModel>) {
    const user = getState().user;
    if (user == null) {
      return EMPTY;
    } else {
      return this.patientService.findById(user.patientId).pipe(
        filter((res) =>
          JSON.stringify(getState().patient) !== JSON.stringify(res),
        ),
        tap((res) => {
          return patchState({
            patient: res,
          })
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

