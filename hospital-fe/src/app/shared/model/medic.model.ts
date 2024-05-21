export interface MedicResponseModel {
  patientId: number;
  firstName: string;
  lastName: string;
  gender: string;
  specialization: Specialization;
}

export enum Specialization {
  SURGEON = 'Surgeon',
  CARDIOLOGIST = 'Cardiologist',
}
