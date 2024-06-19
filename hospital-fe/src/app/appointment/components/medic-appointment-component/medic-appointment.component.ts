import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Medic} from "../../../shared/model/medic.model";
import {Patient} from "../../../shared/model/patient.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppointmentService} from "../../services/appointment.service";
import {AppointmentRequestModel} from "../../../shared/model/appointment.model";
import {mapError} from "../../../shared/error.util";
import {PatientSearchComponent} from "../../../shared/components/patient-search/patient-search.component";
import {MedicSearchComponent} from "../../../shared/components/medic-search/medic-search.component";

@Component({
  selector: 'app-medic-appointment',
  templateUrl: './medic-appointment.component.html',
  styleUrls: ['./medic-appointment.component.scss']
})
export class MedicAppointmentComponent implements OnInit {
  appointmentForm: FormGroup = new FormGroup({
    indications: new FormControl(''),
    observations: new FormControl(''),
    appointmentDate: new FormControl('', [Validators.required]),
    diagnosis: new FormControl('')
  });

  @Input() medic!: Medic;
  @ViewChild(PatientSearchComponent) patientSearchComponent!: PatientSearchComponent;
  @ViewChild(MedicSearchComponent) medicSearchComponent!: MedicSearchComponent;

  selectedPatient: Patient | null = null;
  selectedMedic: Medic | null = null;
  wasAdded: boolean = false;

  errorMessage: string | null = null;

  constructor(private appointmentService: AppointmentService) {
  }

  ngOnInit(): void {
  }

  onPatientSelected(patient: Patient) {
    this.selectedPatient = patient;
  }

  onMedicSelected(medic: Medic) {
    this.selectedMedic = medic;
  }

  isFormValid(): boolean {
    return this.appointmentForm.valid && this.selectedPatient !== null && this.selectedMedic !== null;
  }

  onSubmit() {
    if (this.appointmentForm.valid) {
      const appointmentData: AppointmentRequestModel = {
        patientId: this.selectedPatient!.patientId,
        medicId: this.selectedMedic!.medicId,
        startDate: this.appointmentForm.value.appointmentDate,
        appointmentDetails: this.appointmentForm.value.appointmentDetails,
        diagnosis: this.appointmentForm.value.diagnosis,
        observations: this.appointmentForm.value.observations,
        indications: this.appointmentForm.value.indications
      };

      this.appointmentService.createAppointment(appointmentData)
        .subscribe({
          next: () => {
            this.wasAdded = true;
            this.resetForm();
            this.patientSearchComponent.clearSelection();
            this.medicSearchComponent.clearSelection();
          },
          error: err => {
            const errResponse = mapError(err);
            this.errorMessage = errResponse.message;
          },
        });
    }
  }

  resetForm() {
    this.appointmentForm.reset();
    this.appointmentForm.get('appointmentDate')?.setErrors(null);
  }
}
