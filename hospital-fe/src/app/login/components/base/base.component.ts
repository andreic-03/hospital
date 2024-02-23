import { Injectable, OnDestroy } from '@angular/core';
import { EMPTY, Subject } from 'rxjs';
import {mapError} from "../../../shared/error.util";

@Injectable()
export class BaseComponent implements OnDestroy {
  public unsubscribe$ = new Subject<void>();

  protected genericErrorMessage?: string;

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  protected getGenericErrorHandlingCallback() {
    return (err: any) => {
      const errResponse = mapError(err);
      this.genericErrorMessage = errResponse.errorMessage;
      return EMPTY;
    };
  }
}
