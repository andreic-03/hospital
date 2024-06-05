import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PatientService} from "../../services/patient.service";
import {MatDialogRef} from "@angular/material/dialog";
import {mapError} from "../../error.util";
import {validateCNP} from "../../util/validators.util";

@Component({
  selector: 'app-add-patient-dialog',
  templateUrl: './add-patient-dialog.component.html',
  styleUrls: ['./add-patient-dialog.component.scss'],
})
export class AddPatientDialogComponent {
  addPatientForm: FormGroup = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    citizenship: new FormControl(''),
    country: new FormControl('' ),
    county: new FormControl('' ),
    address: new FormControl(''),
    cnp: new FormControl('', [Validators.required, validateCNP]),
  });

  error = '';

  constructor(private patientService: PatientService,
              private dialogRef: MatDialogRef<AddPatientDialogComponent>) {
  }

  onSubmitAddPatientForm(): void {
    this.patientService.createPatient({
      ...this.addPatientForm.getRawValue(),
    })
      .subscribe({
        next: () => {
          this.dialogRef.close(true);
        },
        error: error => {
          const errResponse = mapError(error);
          this.error = errResponse.message;
        },
      });
  }

  // autoGrow(event: Event): void {
  //   const textarea = event.target as HTMLTextAreaElement;
  //   textarea.style.height = 'auto';
  //   textarea.style.height = textarea.scrollHeight + 'px';
  // }

  onCancelClick(): void {
    this.dialogRef.close();
  }
}
