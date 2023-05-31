import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LoginRequest, LoginResponse } from "../model/auth.model";
import { Observable } from "rxjs";
import { User } from "../model/user.mode";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly LOGIN_URL = '/api/v1/authenticate';
  private readonly CURRENT_USER_URL = '/api/v1/users/info';

  constructor(private http: HttpClient) {}

  public login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.LOGIN_URL, loginRequest);
  }

  public getCurrentUserInfo(username: string): Observable<User> {
    return this.http.get<User>(`${this.CURRENT_USER_URL}/${username}`);
  }
}
