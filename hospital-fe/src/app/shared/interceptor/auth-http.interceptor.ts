import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Navigate } from "@ngxs/router-plugin";
import { Store } from "@ngxs/store";
import { Observable, catchError } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable()
export class AuthHttpInterceptor implements HttpInterceptor {
  constructor(private store: Store) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const cloneRequest = this.addAccessTokenHeaderToReq(req);

    return next.handle(cloneRequest).pipe(
      catchError(err => {
        if (err.status && err.status === 401) {
          localStorage.clear();
          this.store.dispatch(new Navigate(['login']));
        }
        // return there error to the method that called it
        throw err;
      })
    ) as any;
  }

  private addAccessTokenHeaderToReq = (req: HttpRequest<any>) => {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      return req.clone({
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + accessToken,
          'Content-Type': 'application/json',
          'Accept-Language': environment.defaultLang,
        })
      })
    } else {
      return req.clone({
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Accept-Language': environment.defaultLang,
        }),
      });
    }
  }
}
