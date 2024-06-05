import {Component, OnInit} from '@angular/core';
import {AuthState} from "../../../redux/auth.state";
import {Select, Store} from "@ngxs/store";
import {catchError, EMPTY, filter, Observable, switchMap, takeUntil} from "rxjs";
import {Role, User} from "../../../model/user.model";
import {GetCurrentUserInfo, GetMedicInfo, GetPatientInfo, Logout} from "../../../redux/auth.actions";
import {BaseComponent} from "../../../base/base.component";
import {Navigate} from "@ngxs/router-plugin";
import {Medic} from "../../../model/medic.model";
import {Patient} from "../../../model/patient.model";
import {ThemeService} from "../../../services/theme.service";
import {TranslateService} from "@ngx-translate/core";
import {AddPatientDialogComponent} from "../../add-patient-dialog/add-patient-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-header-user',
  templateUrl: './header-user.component.html',
  styleUrls: ['./header-user.component.scss']
})
export class HeaderUserComponent extends BaseComponent implements OnInit {
  Role = Role;
  currentPerson: Medic | Patient | null = null;
  selectedLanguage!: string;
  isDarkTheme!: boolean;

  @Select(AuthState.getCurrentUserInfo)
  currentUser$!: Observable<User>;

  @Select(AuthState.getMedicInfo)
  medic$!: Observable<Medic>;

  @Select(AuthState.getPatientInfo)
  patient$!: Observable<Patient>;

  constructor(private store: Store,
              private themeService: ThemeService,
              private translate: TranslateService,
              private dialog: MatDialog) {
    super();
  }

  ngOnInit(): void {
    this.store.dispatch(new GetCurrentUserInfo());
    this.currentUser$
      .pipe(
        takeUntil(this.unsubscribe$),
        filter(user => !!user), // Filter out null or undefined users
        switchMap(user => {
          if (user.roles.includes(Role.MEDIC)) {
            this.store.dispatch(new GetMedicInfo(user.medicId));
            return this.medic$;
          } else {
            this.store.dispatch(new GetPatientInfo(user.patientId));
            return this.patient$;
          }
        }),
        catchError(error => {
          // Handle error appropriately
          return EMPTY; // or any default value
        })
      )
      .subscribe(person => {
        this.currentPerson = person;
      });

    this.selectedLanguage = localStorage.getItem('selectedLanguage') || 'en';
    this.translate.use(this.selectedLanguage);
    this.isDarkTheme = localStorage.getItem('currentTheme') === 'dark-theme';
    this.themeService.setTheme(this.isDarkTheme ? 'dark-theme' : 'light-theme');
  }

  logout(): void {
    this.store
      .dispatch(new Logout())
      .subscribe(() => this.store.dispatch(new Navigate(["/login"])));
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
        return 'assets/icons/uk-flag.svg';
    }
  }

  openAddPatientDialog(): void {
    const dialogRef = this.dialog.open(AddPatientDialogComponent, {
      width: '800px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        window.location.reload();
      }
    });
  }
}
