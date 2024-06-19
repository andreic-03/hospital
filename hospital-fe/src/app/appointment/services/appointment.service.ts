import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppointmentRequestModel, AppointmentResponseModel} from "../../shared/model/appointment.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  private readonly APPOINTMENT_URL = '/api/appointment';

  constructor(private http: HttpClient) {
  }

  public createAppointment(appointment: AppointmentRequestModel): Observable<AppointmentResponseModel> {
    return this.http.post<AppointmentResponseModel>(`${this.APPOINTMENT_URL}`, appointment);
  }

  public getAppointments(): Observable<AppointmentResponseModel[]> {
    return this.http.get<AppointmentResponseModel[]>(`${this.APPOINTMENT_URL}`);
  }

  public deleteAppointment(appointmentId: number): Observable<void> {
    return this.http.delete<void>(`${this.APPOINTMENT_URL}/${appointmentId}`);
  }
}
