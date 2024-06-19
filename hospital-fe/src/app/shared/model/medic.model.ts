export interface Medic {
  medicId: number;
  patientId: number;
  firstName: string;
  lastName: string;
  gender: string;
  cnp: string
  specialty: Specialty;
  // appointments: Appointment[];
}

export enum Specialty {
  SURGEON = 'Surgeon',
  CARDIOLOGIST = 'Cardiologist',
}
