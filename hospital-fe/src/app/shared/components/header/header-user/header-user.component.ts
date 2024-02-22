import { Component, OnInit } from '@angular/core';
import {AuthState} from "../../../redux/auth.state";
import {Select, Store} from "@ngxs/store";
import {catchError, EMPTY, Observable, switchMap, takeUntil, tap} from "rxjs";
import {Role, User} from "../../../model/user.model";
import {GetCurrentUserInfo, Logout} from "../../../redux/auth.actions";
import {PatientResponseModel} from "../../../model/patient.mode";
import {PatientService} from "../../../services/patient.service";
import {BaseComponent} from "../../../../login/components/base/base.component";
import {MedicService} from "../../../services/medic.service";
import {MedicResponseModel} from "../../../model/medic.mode";
import {Navigate} from "@ngxs/router-plugin";

@Component({
  selector: 'app-header-user',
  templateUrl: './header-user.component.html',
  styleUrls: ['./header-user.component.scss']
})
export class HeaderUserComponent extends BaseComponent implements OnInit {

  @Select(AuthState.getCurrentUserInfo)
  currentUser$!: Observable<User>;

  currentPatient!: PatientResponseModel;
  currentMedic!: MedicResponseModel;

  constructor(private store: Store,
              private patientService: PatientService,
              private medicService: MedicService) {
    super();
  }

  ngOnInit(): void {
    this.store.dispatch(new GetCurrentUserInfo());
    this.currentUser$
      .pipe(
        takeUntil(this.unsubscribe$),
        switchMap(user => this.getUserByType(user)),
        catchError(error => {
          // Handle error appropriately
          return EMPTY; // or any default value
        })
      ).subscribe();
  }

  private getUserByType(user: User): Observable<PatientResponseModel | MedicResponseModel> {
    if (!user) {
      return EMPTY;
    }

    if (user.roles.includes(Role.MEDIC)) {
      return this.medicService.findById(user.medicId).pipe(
        catchError(this.getGenericErrorHandlingCallback()),
        tap((medic: MedicResponseModel) => {
          this.currentMedic = medic;
        })
      );
    } else if (user.roles.includes(Role.PATIENT)) {
      return this.patientService.findById(user.patientId).pipe(
        catchError(this.getGenericErrorHandlingCallback()),
        tap((patient: PatientResponseModel) => {
          this.currentPatient = patient;
        })
      );
    } else {
      return EMPTY;
    }
  }

  logout(): void {
    this.store
      .dispatch(new Logout())
      .subscribe(() => this.store.dispatch(new Navigate(["/login"])));
  }
}
