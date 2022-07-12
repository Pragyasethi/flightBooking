import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Airport } from '../models/airport';
import { Flight } from '../models/flight';
import { AirportService } from '../services/airport.service';
import { FlightService } from '../services/flight.service';

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
    this.findAllAirports();
  }

  // private _filter(value: string): Airport[] {
  //   const filterValue = value.toLowerCase();

  //   return this.airportList.filter(airport => airport.airportLocation.toLowerCase().includes(filterValue));
  // }

  onSearchSubmit(searchform: NgForm) {
    this.searchFlightsWithCondition(searchform); 
  }
  goToFlightList() {
    localStorage.setItem("flightList",JSON.stringify(this.flightList));
    this.router.navigate(['/search']);
  }

  // To get list of all airports for dropdown
  findAllAirports() {
    this.airportService.findAllAirports()
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

  searchFlightsWithCondition(searchform:NgForm){
    this.flightService.searchActiveFlights(searchform)
      .subscribe({
        next: (res: any) => {
          this.flightList=res;
          console.log(this.flightList);

          this.goToFlightList();
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      })
  }
}
