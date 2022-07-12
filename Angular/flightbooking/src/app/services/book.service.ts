import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Booking } from '../models/booking';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  

  host: string = "http://localhost:8090/api/booking/";

  constructor(private http: HttpClient) { }


  createBooking(bookingDetails:Booking){
    console.log(bookingDetails);
    let headers =new HttpHeaders();
    headers.append("Content-Type","application/json");
    headers.append("Accept","*/*");
    return this.http.post(this.host,bookingDetails,{headers:headers});
  }

  findBookings(searchArray: any) {
    const keys = Object.keys(searchArray);
    this.host=this.host.concat("?search=")
    keys.forEach(key => {
      let value = searchArray[key];
      if(key.includes('pnr')|| key.includes('email')){
      this.host = this.host.concat(key).concat(':').concat(value).concat(',');
      }
    })
    console.log('url is ' + this.host);
    return this.http.get(this.host);
  }
}
