import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Airline } from 'src/app/models/airline';

@Component({
  selector: 'app-airline-details',
  templateUrl: './airline-details.component.html',
  styleUrls: ['./airline-details.component.css']
})
export class AirlineDetailsComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: Airline) { }

  ngOnInit(): void {
  }

}
