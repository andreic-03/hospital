import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
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

  public searchMedic(searchTerm: string): Observable<Medic[]> {
    const params = searchTerm
      ? new HttpParams({
        fromObject: { searchTerm },
      })
      : new HttpParams();

    return this.http.get<Medic[]>(this.MEDIC_URL, {
      params,
    });
  }
}
