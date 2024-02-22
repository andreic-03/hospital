import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PatientResponseModel} from "../model/patient.mode";

@Injectable({
  providedIn: 'root',
})
export class MedicService {
  private readonly MEDIC_URL = '/api/medic';

  constructor(private http: HttpClient) {
  }

  public findById(id: number): Observable<PatientResponseModel> {
    return this.http.get<PatientResponseModel>(`${this.MEDIC_URL}/${id}`);
  }
}
