export interface ErrorResponse {
  errorCode: string;
  errorMessage: string;
  context: { [key: string]: any };
}
