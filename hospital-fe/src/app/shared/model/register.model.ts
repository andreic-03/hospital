export interface RegisterStepOneModelRequest {
  username: string;
  password: string;
  email: string;
  roles: string[];
}

export interface RegisterStepOneModelResponse {
  userId: number;
  username: string;
  email: string;
}

export interface RegisterStepTwoModelRequest {
  token: string;
  firstName: string;
  lastName: string;
  cnp: number;
  gender: string;
}
