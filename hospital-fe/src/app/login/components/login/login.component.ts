import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngxs/store';
import { Login } from 'src/app/shared/redux/auth.actions';
import { Navigate } from '@ngxs/router-plugin';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  form: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  constructor(private store: Store) { }

  ngOnInit(): void {
  }

  login() {
    const username = this.form.get('username')?.value;
    const password = this.form.get('password')?.value;

    this.store
      .dispatch(new Login(username, password))
      .subscribe(res => {
        if (res) {
          this.store.dispatch(new Navigate(["/"]));
        }
      })
  }

}
