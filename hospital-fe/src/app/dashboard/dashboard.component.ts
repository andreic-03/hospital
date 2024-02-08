import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  showNewTitle: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  sugiPula() {
    this.showNewTitle = !this.showNewTitle;
  }
}
