export interface User {
  id: number;
  firstName: string;
  lastName: string;
  phone: string;
  email: string;
  gender: string;
  roles: Role[];
}

export enum Role {
  ADMIN = 'Admin',
  USER = 'User'
}
