export interface Patient {
  patientId: number;
  firstName: string;
  lastName: string;
  citizenship: string;
  dateOfBirth: string;
  country: string;
  county: string;
  city: string;
  address: string;
  maritalStatus: string;
  cnp: string;
  gender: string;
  // appointments: Appointment[];
}
