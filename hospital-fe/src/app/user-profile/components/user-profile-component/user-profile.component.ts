import {Component, OnInit} from '@angular/core';
import {Medic} from "../../../shared/model/medic.model";
import {Patient} from "../../../shared/model/patient.model";
import {Select, Store} from "@ngxs/store";
import {AuthState} from "../../../shared/redux/auth.state";
import {User} from "../../../shared/model/user.model";
import {Observable, switchMap} from "rxjs";
import {PasswordChangeDialogComponent} from "../password-change-dialog/password-change-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {NgForm} from "@angular/forms";
import {UserProfileService} from "../../services/user-profile.service";
import {GetCurrentUserInfo} from "../../../shared/redux/auth.actions";
import {mapError} from "../../../shared/error.util";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit{
  editingPhoneNumber = false;
  editingEmail = false;
  isEditing = false;
  editablePhoneNumber!: string;
  editableEmail!: string;
  updatedUser!: User;

  updateUserErrorMessage: string | null = null;

  @Select(AuthState.getCurrentUserInfo)
  currentUser$!: Observable<User>;

  @Select(AuthState.getMedicInfo)
  medic$!: Observable<Medic>;

  @Select(AuthState.getPatientInfo)
  patient$!: Observable<Patient>;

  constructor(private dialog: MatDialog,
              private userService: UserProfileService,
              private store: Store) {}

  ngOnInit(): void {
  }

  openPasswordChangeDialog(): void {
    this.dialog.open(PasswordChangeDialogComponent, {
      width: '600px',
    });
  }

  toggleEdit(field: 'phone' | 'email') {
    this.isEditing = true;
    if (field === 'phone') {
      this.editingPhoneNumber = true;
      this.editingEmail = false;
      this.currentUser$.subscribe(user => {
        this.editablePhoneNumber = user.phoneNumber;
      });
    } else {
      this.editingEmail = true;
      this.editingPhoneNumber = false;
      this.currentUser$.subscribe(user => {
        this.editableEmail = user.email;
      });
    }
  }

  cancelEdits() {
    this.isEditing = false;
    this.editingPhoneNumber = false;
    this.editingEmail = false;
  }

  onEmailPhoneNumberSubmit(form: NgForm) {
    if (form.valid) {
      this.currentUser$.pipe(
        switchMap(user => {
          let updatedUser: User;

          if (this.editingPhoneNumber) {
            updatedUser = { ...user, phoneNumber: this.editablePhoneNumber };
          } else if (this.editingEmail) {
            updatedUser = { ...user, email: this.editableEmail };
          } else {
            updatedUser = user;
          }

          return this.userService.updateUser(updatedUser);
        })
      ).subscribe({
        next: () => {
          this.store.dispatch(new GetCurrentUserInfo());
          this.editingPhoneNumber = false;
          this.editingEmail = false;
          this.isEditing = false;
        },
        error: error => {
          const errResponse = mapError(error);
          this.updateUserErrorMessage = errResponse.message;
        },
      });
    }
  }
}
