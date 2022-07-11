import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AirportService } from '../services/airport.service';

@Component({
  selector: 'app-search-flight',
  templateUrl: './search-flight.component.html',
  styleUrls: ['./search-flight.component.css']
})
export class SearchFlightComponent implements OnInit {

  flightList: any[] = [];
  departureDate!:string;

  constructor(private router: Router) {

  }

  ngOnInit(): void {
    this.flightList=JSON.parse(localStorage.getItem("flightList")||"");
    this.departureDate=localStorage.getItem('date')||"";
    //localStorage.clear();
  }

  bookTicket(flightId:number){
    this.router.navigate(['/book'],
    { queryParams: { id:  flightId, date:(localStorage.getItem('date'))} }
  );
  }


}
