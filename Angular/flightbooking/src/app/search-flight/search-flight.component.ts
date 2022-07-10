import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AirportService } from '../services/airport.service';

@Component({
  selector: 'app-search-flight',
  templateUrl: './search-flight.component.html',
  styleUrls: ['./search-flight.component.css']
})
export class SearchFlightComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }


}
