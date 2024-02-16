import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {RegisterStepOneModelRequest, RegisterStepOneModelResponse} from "../model/register.model";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  private readonly REGISTER_URL = '/api/user/registration/step-one';

  constructor(private http: HttpClient) {}

  public registerStepOne(registerStepOneRequest: RegisterStepOneModelRequest): Observable<RegisterStepOneModelResponse> {
    return this.http.post<RegisterStepOneModelResponse>(this.REGISTER_URL, registerStepOneRequest);
  }
}
