import { Component, OnInit } from '@angular/core';
import {Store} from "@ngxs/store";
import {Navigate} from "@ngxs/router-plugin";
import {Router} from "@angular/router";
import {ThemeService} from "../../services/theme.service";
import {TranslateService} from "@ngx-translate/core";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";

@Component({
  selector: 'app-not-logged-in-header',
  templateUrl: './not-logged-in-header.component.html',
  styleUrls: ['./not-logged-in-header.component.scss']
})
export class NotLoggedInHeaderComponent implements OnInit {
  showCreateAccountButton: boolean = false;
  showSignInButton: boolean = false;
  isDarkTheme!: boolean;
  selectedLanguage!: string;
  isMobile!: boolean;

  constructor(private store: Store,
              private router: Router,
              private translate: TranslateService,
              private themeService: ThemeService,
              private breakpointObserver: BreakpointObserver) { }

  ngOnInit(): void {
    this.breakpointObserver.observe([Breakpoints.Handset])
      .subscribe(result => {
        this.isMobile = result.matches;
      });

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

    this.selectedLanguage = localStorage.getItem('selectedLanguage') || 'en';
    this.translate.use(this.selectedLanguage);
    this.isDarkTheme = localStorage.getItem('currentTheme') === 'dark-theme';
    this.themeService.setTheme(this.isDarkTheme ? 'dark-theme' : 'light-theme');
  }

  navigateToSignUp() {
    this.store.dispatch(new Navigate(['/register/step-one']));
  }

  navigateToLogin() {
    this.store.dispatch(new Navigate(['/login']));
  }

  toggleTheme(): void {
    this.themeService.toggleTheme();
  }

  switchLanguage(language: string) {
    this.selectedLanguage = language;
    this.translate.use(language);
    localStorage.setItem('selectedLanguage', language);
  }

  getFlagUrl(language: string): string {
    switch (language) {
      case 'en':
        return 'assets/icons/uk-flag.svg';
      case 'ro':
        return 'assets/icons/romania-flag.svg';
      default:
        return 'assets/icons/uk-flag.svg'; // default to English if not found
    }
  }

}
