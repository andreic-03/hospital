import {Component, Input, OnInit} from '@angular/core';
import {Medic} from "../../../shared/model/medic.model";
import {Patient} from "../../../shared/model/patient.model";
import {PatientService} from "../../../shared/services/patient.service";
import {catchError, combineLatest, EMPTY} from "rxjs";
import {mapError} from "../../../shared/error.util";
import {AddPatientDialogComponent} from "../../../shared/components/add-patient-dialog/add-patient-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {AppointmentService} from "../../../appointment/services/appointment.service";
import {AppointmentResponseModel} from "../../../shared/model/appointment.model";

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

  allAppointments: AppointmentResponseModel[] = [];
  firstThreeAppointments: {
    appointment: AppointmentResponseModel,
    patient: Patient | undefined }[] = [];

  appointmentsWithPatients: {
    appointment: AppointmentResponseModel,
    patient: Patient | undefined }[] = [];

  constructor(private patientService: PatientService,
              private appointmentService: AppointmentService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadPatients();
    this.loadData();
  }

  loadData(): void {
    combineLatest([
      this.patientService.getPatientsByMedic(this.medic.medicId).pipe(catchError(err => {
        const errResponse = mapError(err);
        this.error = errResponse.message;
        return EMPTY;
      })),
      this.appointmentService.getAppointments().pipe(catchError(err => {
        const errResponse = mapError(err);
        this.error = errResponse.message;
        return EMPTY;
      }))
    ]).subscribe(([patients, appointments]) => {
      this.allPatients = patients;
      this.firstThreePatients = this.allPatients.slice(0, 3);

      this.allAppointments = appointments;

      this.matchAppointmentsWithPatients();

      this.firstThreeAppointments = this.appointmentsWithPatients.slice(0, 3);
    });
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

  matchAppointmentsWithPatients() {
    this.appointmentsWithPatients = this.allAppointments.map(appointment => {
      const patient = this.allPatients.find(p => p.patientId === appointment.patientId);
      return { appointment, patient };
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

  deleteAppointment(appointmentId: number): void {
      this.appointmentService.deleteAppointment(appointmentId)
        .pipe(
          catchError(err => {
            const errResponse = mapError(err);
            this.error = errResponse.message;
            return EMPTY;
          })
        )
        .subscribe(() => {
          this.appointmentsWithPatients = this.appointmentsWithPatients.filter(item => item.appointment.appointmentId !== appointmentId);
          this.firstThreeAppointments = this.appointmentsWithPatients.slice(0, 3);
          this.loadPatients();
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
