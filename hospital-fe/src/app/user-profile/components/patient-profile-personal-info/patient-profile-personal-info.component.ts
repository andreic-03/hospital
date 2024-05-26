import {Component, Input, OnInit} from '@angular/core';
import {Select, Store} from "@ngxs/store";
import {AuthState} from "../../../shared/redux/auth.state";
import {Observable} from "rxjs";
import {User} from "../../../shared/model/user.model";
import {MedicResponseModel} from "../../../shared/model/medic.model";
import {Patient} from "../../../shared/model/patient.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PatientService} from "../../../shared/services/patient.service";
import {mapError} from "../../../shared/error.util";
import {GetCurrentUserInfo} from "../../../shared/redux/auth.actions";

@Component({
  selector: 'app-patient-profile-personal-info',
  templateUrl: './patient-profile-personal-info.component.html',
  styleUrls: ['./patient-profile-personal-info.component.scss']
})
export class PatientProfilePersonalInfoComponent implements OnInit {
  @Input() patient!: Patient;
  countries: string[] = ['Germany', 'United Kingdom', 'France', 'Romania', 'Spain'];
  counties: string[] = ['Alba', 'Cluj', 'Bucuresti', 'Timisoara', 'Salaj'];
  maritalStatuses: string[] = ['Casatorit', 'Necasatorit'];

  isEditMode = false;
  profileForm!: FormGroup;

  profileErrorMessage: string | null = null;

  constructor(private formBuilder: FormBuilder,
              private patientService: PatientService,
              private store: Store) {
  }

  ngOnInit() {
    this.initProfileForm();
  }

  private initProfileForm() {

    this.profileForm = this.formBuilder.group({
      firstName: this.formBuilder.nonNullable.control(this.patient.firstName),
      lastName: this.formBuilder.nonNullable.control(this.patient.firstName),
      citizenship: this.formBuilder.nonNullable.control(this.patient.citizenship),
      cnp: this.formBuilder.nonNullable.control(this.patient.cnp),
      dateOfBirth: this.formBuilder.nonNullable.control(this.patient.dateOfBirth),
      gender: this.formBuilder.nonNullable.control(this.patient.gender),
      country: this.formBuilder.control(this.patient.country),
      county: this.formBuilder.control(this.patient.county),
      city: this.formBuilder.control(this.patient.city),
      address: this.formBuilder.control(this.patient.address),
      maritalStatus: this.formBuilder.control(this.patient.maritalStatus),
    })
  }

  enableEdit() {
    this.isEditMode = true;
  }

  cancelEdit() {
    this.isEditMode = false;
  }

  onSubmitProfileForm() {
    this.profileErrorMessage = null;

    this.patientService
      .updatePatient(this.patient.patientId, this.profileForm.getRawValue())
      .subscribe({
        next: (updatedPatient) => {
          this.patient = updatedPatient;
          this.isEditMode = false;
        },
        error: err => {
          const errResponse = mapError(err);
          this.profileErrorMessage = errResponse.message;
        },
      });
  }
}
