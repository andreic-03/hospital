import {Component, OnInit} from '@angular/core';
import {MedicResponseModel} from "../shared/model/medic.mode";
import {PatientResponseModel} from "../shared/model/patient.mode";
import {Select, Store} from "@ngxs/store";
import {AuthState} from "../shared/redux/auth.state";
import {Observable} from "rxjs";
import {Role, User} from "../shared/model/user.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  currentPerson: MedicResponseModel | PatientResponseModel | null = null;
  Role = Role;

  @Select(AuthState.getCurrentUserInfo)
  currentUser$!: Observable<User>;

  constructor(private store: Store) { }

  ngOnInit(): void {
  }

  receiveCurrentPerson(person: MedicResponseModel | PatientResponseModel) {
    this.currentPerson = person;
  }
}
