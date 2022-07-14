import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Flight } from 'src/app/models/flight';
import { FlightService } from 'src/app/services/flight.service';
import { FlightDetailsComponent } from '../flight-details/flight-details.component';

@Component({
  selector: 'app-flight',
  templateUrl: './flight.component.html',
  styleUrls: ['./flight.component.css']
})
export class FlightComponent implements OnInit {
  flightList: any[] = [];
  errorMessage: string = "";
  params!: Params;


  constructor(private router: Router, private flightService: FlightService,
    private route: ActivatedRoute,public dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(
      params => this.params = params);
    this.searchFlights(this.params);

  }
  openDialog(flightDetails: Flight) {
    this.dialog.open(FlightDetailsComponent, { data: flightDetails });
  }
  searchFlights(params: any) {
    this.flightService.searchAllFlights(params)
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

  editFlight(flightId:any){

  }

}
