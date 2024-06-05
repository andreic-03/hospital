export interface Medic {
  patientId: number;
  firstName: string;
  lastName: string;
  gender: string;
  cnp: string
  specialization: Specialization;
  // appointments: Appointment[];
}

export enum Specialization {
  SURGEON = 'Surgeon',
  CARDIOLOGIST = 'Cardiologist',
}
