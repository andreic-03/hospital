import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {MedicResponseModel} from "../../model/medic.mode";
import {PatientResponseModel} from "../../model/patient.mode";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  @Output() currentPerson = new EventEmitter<MedicResponseModel | PatientResponseModel>();

  constructor() { }

  ngOnInit(): void {
  }

  receiveCurrentPerson(person: MedicResponseModel | PatientResponseModel) {
    this.currentPerson.emit(person);
  }

}
