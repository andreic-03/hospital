import {Component, OnInit} from '@angular/core';
import {Select} from "@ngxs/store";
import {AuthState} from "../../shared/redux/auth.state";
import {Observable} from "rxjs";
import {User} from "../../shared/model/user.model";
import {MedicResponseModel} from "../../shared/model/medic.model";
import {Patient} from "../../shared/model/patient.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  @Select(AuthState.getCurrentUserInfo)
  currentUser$!: Observable<User>;

  @Select(AuthState.getMedicInfo)
  medic$!: Observable<MedicResponseModel>;

  @Select(AuthState.getPatientInfo)
  patient$!: Observable<Patient>;

  ngOnInit(): void {
  }

}
