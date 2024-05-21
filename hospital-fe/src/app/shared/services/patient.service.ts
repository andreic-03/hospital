import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PatientResponseModel} from "../model/patient.model";

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private readonly PATIENT_URL = '/api/patient';

  constructor(private http: HttpClient) {
  }

  public findById(id: number): Observable<PatientResponseModel> {
    return this.http.get<PatientResponseModel>(`${this.PATIENT_URL}/${id}`);
  }
}
