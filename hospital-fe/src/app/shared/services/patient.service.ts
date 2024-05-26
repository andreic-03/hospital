import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Patient} from "../model/patient.model";
import {
  PatientProfilePersonalInfoComponent
} from "../../user-profile/components/patient-profile-personal-info/patient-profile-personal-info.component";

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private readonly PATIENT_URL = '/api/patient';

  constructor(private http: HttpClient) {
  }

  public findById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.PATIENT_URL}/${id}`);
  }

  public updatePatient(id: number, patient: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${this.PATIENT_URL}/${id}`, patient);
  }
}
