import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {
  RegisterStepOneModelRequest,
  RegisterStepOneModelResponse,
  RegisterStepTwoModelRequest
} from "../model/register.model";
import {Observable} from "rxjs";
import {Patient} from "../model/patient.model";


@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  private readonly REGISTER_STEP_ONE_URL = '/api/user/registration/step-one';
  private readonly REGISTER_STEP_TWO_URL = '/api/patient/registration/step-two';

  constructor(private http: HttpClient) {}

  public registerStepOne(registerStepOneRequest: RegisterStepOneModelRequest): Observable<RegisterStepOneModelResponse> {
    return this.http.post<RegisterStepOneModelResponse>(this.REGISTER_STEP_ONE_URL, registerStepOneRequest);
  }

  public registerStepTwo(registerStepTwoRequest: RegisterStepTwoModelRequest): Observable<Patient> {
    return this.http.post<Patient>(this.REGISTER_STEP_TWO_URL, registerStepTwoRequest);
  }
}
