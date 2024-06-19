import {Component, OnInit} from '@angular/core';
import {Select} from "@ngxs/store";
import {AuthState} from "../../../shared/redux/auth.state";
import {Observable} from "rxjs";
import {User} from "../../../shared/model/user.model";
import {Medic} from "../../../shared/model/medic.model";
import {Patient} from "../../../shared/model/patient.model";

@Component({
  selector: 'app-appointment-component',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentComponent implements OnInit {
  @Select(AuthState.getCurrentUserInfo)
  currentUser$!: Observable<User>;

  @Select(AuthState.getMedicInfo)
  medic$!: Observable<Medic>;

  @Select(AuthState.getPatientInfo)
  patient$!: Observable<Patient>;

  ngOnInit(): void {
  }
}
