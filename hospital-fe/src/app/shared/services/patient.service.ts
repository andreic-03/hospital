import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Patient} from "../model/patient.model";

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private readonly PATIENT_URL = '/api/patient';
  private readonly CREATED_BY_CURRENT_USER = this.PATIENT_URL + '/createdByCurrentUser'
  private readonly PATIENTS_BY_MEDIC = this.PATIENT_URL + '/medic'

  constructor(private http: HttpClient) {
  }

  public findById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.PATIENT_URL}/${id}`);
  }

  public updatePatient(id: number, patient: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${this.PATIENT_URL}/${id}`, patient);
  }

  public getPatientCreatedByCurrentUser(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.CREATED_BY_CURRENT_USER}`);
  }

  public deletePatient(patientId: number): Observable<void> {
    return this.http.delete<void>(`${this.PATIENT_URL}/${patientId}`);
  }

  public createPatient(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(`${this.PATIENT_URL}`, patient);
  }

  public searchPatient(searchTerm: string): Observable<Patient[]> {
    const params = searchTerm
      ? new HttpParams({
        fromObject: {searchTerm},
      })
      : new HttpParams();

    return this.http.get<Patient[]>(this.PATIENT_URL, {
      params,
    });
  }

  public getPatientsByMedic(medicId: number): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.PATIENTS_BY_MEDIC}/${medicId}`);
  }
}
