import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AirportService } from '../../services/airport.service';
import { FlightService } from '../../services/flight.service';

@Component({
  selector: 'app-search-flight',
  templateUrl: './search-flight.component.html',
  styleUrls: ['./search-flight.component.css']
})
export class SearchFlightComponent implements OnInit {

  flightList: any[] = [];
  departureDate!: string;
  errorMessage: string = "";
  params!: Params;


  constructor(private router: Router, private flightService: FlightService,
    private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(
      params => this.params = params);
    this.searchFlightsWithCondition(this.params);
    this.departureDate = this.params['date'];
  }

  bookTicket(flightId: number) {
    this.router.navigate(['/bookflight'],
      { queryParams: { id: flightId, date: (localStorage.getItem('date')) } }
    );
  }

  searchFlightsWithCondition(params: any) {
    this.flightService.searchActiveFlights(params)
      .subscribe({
        next: (res: any) => {
          this.flightList = res;
          console.log(this.flightList);
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      })
  }


}
