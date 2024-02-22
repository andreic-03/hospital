import { HttpErrorResponse } from '@angular/common/http';
import {ErrorResponse} from "./model/error.model";

export const mapError = (response: HttpErrorResponse): ErrorResponse => {
  //TODO: check if actual error has this format and return a general error instead
  //TODO: provide translation for general error, check this server side as well
  return response.error as ErrorResponse;
};
