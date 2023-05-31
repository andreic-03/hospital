export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  jwtToken: string;
}
