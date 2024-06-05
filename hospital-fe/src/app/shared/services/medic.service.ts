import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Medic} from "../model/medic.model";

@Injectable({
  providedIn: 'root',
})
export class MedicService {
  private readonly MEDIC_URL = '/api/medic';

  constructor(private http: HttpClient) {
  }

  public findById(id: number): Observable<Medic> {
    return this.http.get<Medic>(`${this.MEDIC_URL}/${id}`);
  }
}
