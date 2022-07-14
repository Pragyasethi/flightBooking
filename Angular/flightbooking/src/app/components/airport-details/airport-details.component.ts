import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Airport } from 'src/app/models/airport';

@Component({
  selector: 'app-airport-details',
  templateUrl: './airport-details.component.html',
  styleUrls: ['./airport-details.component.css']
})
export class AirportDetailsComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: Airport) { }

  ngOnInit(): void {
  }

}
