export interface User {
  id: number;
  username: string;
  status: string;
  email: string;
  phoneNumber: string;
  roles: Role[];
  medicId: number;
  patientId: number;
}

export enum Role {
  ADMIN = 'Admin',
  MEDIC = 'MEDIC',
  PATIENT = 'PATIENT',
}
