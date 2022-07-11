import { Component, OnInit } from '@angular/core';
import { Booking } from '../models/booking';

@Component({
  selector: 'app-book-flight',
  templateUrl: './book-flight.component.html',
  styleUrls: ['./book-flight.component.css']
})
export class BookFlightComponent implements OnInit {

  booking: Booking = new Booking();
  submitted = false;
  passengerCount: any[]=[];

  constructor() { }

  ngOnInit(): void {
  }

  newBooking(): void {
    this.submitted = false;
    this.booking = new Booking();
  }
  onSubmit() {
    this.submitted = true;
  }

  counter(i: number) {
    return new Array(i);

  }
  showDiv(j:number){
    this.passengerCount= new Array(j);
    console.log(this.passengerCount);
  }
}
