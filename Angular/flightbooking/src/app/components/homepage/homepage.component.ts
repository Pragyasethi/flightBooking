import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {  NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Airport } from '../../models/airport';
import { Flight } from '../../models/flight';
import { AirportService } from '../../services/airport.service';
import { FlightService } from '../../services/flight.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {


  errorMessage: string = "";
  airportList: Airport[] = [];
  flightList: Flight[] = [];

  constructor(private flightService: FlightService, private airportService: AirportService, private router: Router) { }

  ngOnInit(): void {
    this.findAllActiveAirports();
  }

  onSearchSubmit(searchform: NgForm) {
    this.router.navigate(['/flights/search'],
      { queryParams: { date: searchform.value['scheduledfor'], source: searchform.value['source'], destination: searchform.value['destination'] } }
    );
  }


  // To get list of all airports for dropdown
  findAllActiveAirports() {
    this.airportService.findAllActiveAirports()
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.airportList = res;
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      })
  }
}
