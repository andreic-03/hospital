import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MedicResponseModel} from "../model/medic.model";

@Injectable({
  providedIn: 'root',
})
export class MedicService {
  private readonly MEDIC_URL = '/api/medic';

  constructor(private http: HttpClient) {
  }

  public findById(id: number): Observable<MedicResponseModel> {
    return this.http.get<MedicResponseModel>(`${this.MEDIC_URL}/${id}`);
  }
}
