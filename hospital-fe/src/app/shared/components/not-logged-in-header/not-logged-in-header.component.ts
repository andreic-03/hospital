import { Component, OnInit } from '@angular/core';
import {Store} from "@ngxs/store";
import {Navigate} from "@ngxs/router-plugin";
import {Router} from "@angular/router";

@Component({
  selector: 'app-not-logged-in-header',
  templateUrl: './not-logged-in-header.component.html',
  styleUrls: ['./not-logged-in-header.component.scss']
})
export class NotLoggedInHeaderComponent implements OnInit {

  showCreateAccountButton: boolean = false;
  showSignInButton: boolean = false;

  constructor(private store: Store,
              private router: Router) { }

  ngOnInit(): void {
    const currentRoute = this.router.url;
    if (currentRoute === '/login') {
      this.showCreateAccountButton = true;
    }
    if (currentRoute === '/register/step-one') {
      this.showSignInButton = true;
    }
    if (currentRoute.startsWith('/register/step-two')) {
      this.showSignInButton = true;
    }
  }

  navigateToSignUp() {
    this.store.dispatch(new Navigate(['/register/step-one']));
  }

  navigateToLogin() {
    this.store.dispatch(new Navigate(['/login']));
  }

}
