import {Component, Input, OnInit} from '@angular/core';
import {Medic} from "../../shared/model/medic.model";
import {Patient} from "../../shared/model/patient.model";
import {PatientService} from "../../shared/services/patient.service";
import {catchError, EMPTY} from "rxjs";
import {mapError} from "../../shared/error.util";
import {AddPatientDialogComponent} from "../../shared/components/add-patient-dialog/add-patient-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-medic-dashboard',
  templateUrl: './medic-dashboard.component.html',
  styleUrls: ['./medic-dashboard.component.scss']
})
export class MedicDashboardComponent implements OnInit {
  @Input() medic!: Medic;
  error = '';

  allPatients: Patient[] = [];
  firstThreePatients: Patient[] = [];

  constructor(private patientService: PatientService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients(): void {
    this.patientService.getPatientCreatedByCurrentUser()
      .pipe(
        catchError(err => {
          const errResponse = mapError(err);
          this.error = errResponse.message;
          return EMPTY;
        })
      )
      .subscribe(res => {
        this.allPatients = res;
        this.firstThreePatients = this.allPatients.slice(0, 3);
      });
  }

  deletePatient(patientId: number): void {
      this.patientService.deletePatient(patientId)
        .pipe(
          catchError(err => {
            const errResponse = mapError(err);
            this.error = errResponse.message;
            return EMPTY;
          })
        )
        .subscribe(() => {
          this.allPatients = this.allPatients.filter(patient => patient.patientId !== patientId);
          this.firstThreePatients = this.allPatients.slice(0, 3);
        });
    }

  openAddPatientDialog(): void {
    const dialogRef = this.dialog.open(AddPatientDialogComponent, {
      width: '800px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadPatients(); // Refresh the patient list if a new patient was added
      }
    });
  }
}
