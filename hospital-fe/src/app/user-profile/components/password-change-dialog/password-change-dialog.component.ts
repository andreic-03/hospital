import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordStrengthValidator} from "../../../sign-up/step-one/password.validator";
import {createPasswordComparisonValidator} from "../../../shared/util/validators.util";
import {UserProfileService} from "../../services/user-profile.service";
import {Store} from "@ngxs/store";
import {Navigate} from "@ngxs/router-plugin";
import {mapError} from "../../../shared/error.util";

@Component({
  selector: 'app-password-change-dialog',
  templateUrl: './password-change-dialog.component.html',
  styleUrls: ['./password-change-dialog.component.scss']
})
export class PasswordChangeDialogComponent {
  changePasswordForm: FormGroup = new FormGroup({
    oldPassword: new FormControl('', [Validators.required]),
    newPassword: new FormControl('', [
      Validators.required,
      PasswordStrengthValidator,
    ]),
    confirmNewPassword: new FormControl('', [Validators.required]),
  });

  currentPassword!: string;
  newPassword!: string;
  confirmNewPassword!: string;

  shouldHideOldPassword = true;
  shouldHideNewPassword = true;
  shouldHideConfirmPassword = true;

  passwordErrorMessage: string | null = null;

  constructor(private dialogRef: MatDialogRef<PasswordChangeDialogComponent>,
              private userProfileService: UserProfileService,
              private store: Store) {
    this.changePasswordForm.addValidators(
      createPasswordComparisonValidator(
        this.changePasswordForm.get('newPassword'),
        this.changePasswordForm.get('confirmNewPassword')
      )
    );
  }

  onSubmitPasswordForm(): void {
    this.userProfileService.changeUserPassword({
      ...this.changePasswordForm.getRawValue(),
    })
      .subscribe({
        next: () => {
          this.dialogRef.close();
          this.store.dispatch(new Navigate(['/login']))
        },
        error: error => {
          const errResponse = mapError(error);
          this.passwordErrorMessage = errResponse.message;
        },
      });

  }

  onCancelClick(): void {
    this.dialogRef.close();
  }
}
