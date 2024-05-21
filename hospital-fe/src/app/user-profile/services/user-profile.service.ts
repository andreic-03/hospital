import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../shared/model/user.model";
import {ChangeUserPassword} from "../model/change-user-password.model";

@Injectable({
  providedIn: 'root',
})
export class UserProfileService {
  private readonly USER_URL = '/api/user';
  private readonly PASSWORD_RESET_URL = this.USER_URL + '/password-reset';

  constructor(private http: HttpClient) {
  }

  public updateUser(user: User): Observable<User>  {
    return this.http.put<User>(`${this.USER_URL}`, user);
  }

  public changeUserPassword(changeUserPassword: ChangeUserPassword): Observable<ChangeUserPassword> {
    return this.http.put<ChangeUserPassword>(`${this.PASSWORD_RESET_URL}`, changeUserPassword);
  }

}
