import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl} from "@angular/forms";
import {Patient} from "../../model/patient.model";
import {Store} from "@ngxs/store";
import {PatientService} from "../../services/patient.service";
import {debounceTime, distinctUntilChanged, filter, finalize, switchMap, tap} from "rxjs";
import {mapError} from "../../error.util";
import {BaseComponent} from "../../base/base.component";

@Component({
  selector: 'app-patient-search',
  templateUrl: './patient-search.component.html',
  styleUrls: ['./patient-search.component.scss']
})
export class PatientSearchComponent extends BaseComponent implements OnInit {
  @Output() patientSelected = new EventEmitter<Patient>();

  searchCtrl: FormControl<string>;
  filteredResult: Patient[] = [];
  selectedResult: Patient | null = null;
  isLoading = false;
  hasNoResults = false;
  minLengthTerm = 3;
  errorMessage!: string;

  constructor(private store: Store,
              private patientService: PatientService,
              private formBuilder: FormBuilder) {
    super();
    this.searchCtrl = formBuilder.nonNullable.control('');
  }

  ngOnInit(): void {
    this.searchCtrl.valueChanges
      .pipe(
        filter(res => res !== null && res.length >= this.minLengthTerm),
        distinctUntilChanged(),
        debounceTime(500),
        tap(() => {
          this.errorMessage = '';
          this.filteredResult = [];
          this.hasNoResults = false;
          this.isLoading = true;
        }),
        switchMap(searchTerm => this.patientService
          .searchPatient(searchTerm)
          .pipe(finalize(() => (this.isLoading = false)))
        )
      ).subscribe({
      next: (patientResponse: Patient[]) => {
        this.filteredResult = patientResponse;
        this.hasNoResults = patientResponse.length === 0;
      },
      error: err => {
        const errResponse = mapError(err);
        this.errorMessage = errResponse.message;
        this.isLoading = false;
      },
    });
  }

  onSelected(event: any) {
    this.selectedResult = event.option.value;
    if (this.selectedResult) {
      this.patientSelected.emit(this.selectedResult);
    }
  }

  displayWith(value: any) {
    return value ? value.firstName + ' ' + value.lastName : '';
  }

  clearSelection() {
    this.selectedResult = null;
    this.filteredResult = [];
    this.searchCtrl.setValue('');
  }

}
