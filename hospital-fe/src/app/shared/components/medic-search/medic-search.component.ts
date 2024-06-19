import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl} from "@angular/forms";
import {Store} from "@ngxs/store";
import {debounceTime, distinctUntilChanged, filter, finalize, switchMap, tap} from "rxjs";
import {mapError} from "../../error.util";
import {Medic} from "../../model/medic.model";
import {MedicService} from "../../services/medic.service";
import {BaseComponent} from "../../base/base.component";

@Component({
  selector: 'app-medic-search',
  templateUrl: './medic-search.component.html',
  styleUrls: ['./medic-search.component.scss']
})
export class MedicSearchComponent extends BaseComponent implements OnInit {
  @Output() medicSelected = new EventEmitter<Medic>();

  searchCtrl: FormControl<string>;
  filteredResult: Medic[] = [];
  selectedResult: Medic | null = null;
  isLoading = false;
  hasNoResults = false;
  minLengthTerm = 3;
  errorMessage!: string;

  constructor(private store: Store,
              private medicService: MedicService,
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
        switchMap(searchTerm => this.medicService
          .searchMedic(searchTerm)
          .pipe(finalize(() => (this.isLoading = false)))
        )
      ).subscribe({
      next: (medicResponse: Medic[]) => {
        this.filteredResult = medicResponse;
        this.hasNoResults = medicResponse.length === 0;
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
      this.medicSelected.emit(this.selectedResult);
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
