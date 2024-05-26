import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {Store} from "@ngxs/store";
import {map, Observable} from "rxjs";
import {AuthState} from "./auth.state";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  constructor(private store: Store, private router: Router) {}

  canActivate(): Observable<boolean> | Promise<boolean> | boolean {
    return this.store.select(AuthState.isAuthenticated).pipe(
      map(isAuthenticated => {
        if (!isAuthenticated) {
          this.router.navigate(['/login']);
          return false;
        }
        return true;
      })
    );
  }
}
