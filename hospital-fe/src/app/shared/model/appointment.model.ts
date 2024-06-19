export interface AppointmentRequestModel {
  patientId: number;
  startDate: string;
  appointmentDetails: string;
  diagnosis: string;
  observations: string;
  indications: string;
  medicId: number;
}

export interface AppointmentResponseModel {
  appointmentId: number;
  patientId: number;
  startDate: string;
  appointmentDetails: string;
  diagnosis: string;
  observations: string;
  indications: string;
  medicId: number;
}
