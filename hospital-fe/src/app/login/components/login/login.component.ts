import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngxs/store';
import { Login } from 'src/app/shared/redux/auth.actions';
import { Navigate } from '@ngxs/router-plugin';
import {BaseComponent} from "../base/base.component";
import {catchError, EMPTY, takeUntil} from "rxjs";
import {mapError} from "../../../shared/error.util";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent extends BaseComponent implements OnInit {

  error = '';

  shouldHidePassword = true;

  form: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  constructor(private store: Store) {
    super();
  }

  ngOnInit(): void {
  }

  login() {
    const username = this.form.get('username')?.value;
    const password = this.form.get('password')?.value;

    this.store
      .dispatch(new Login(username, password))
      .pipe(
        takeUntil(this.unsubscribe$),
        catchError(err => {
          const errorResponse = mapError(err);
          this.error = errorResponse.errorMessage;
          return EMPTY;
        })
      )
      .subscribe(res => {
        if (res) {
          this.store.dispatch(new Navigate(['/dashboard']));
        }
      })
  }

}
