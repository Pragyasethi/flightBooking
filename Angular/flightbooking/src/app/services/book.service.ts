import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Booking } from '../models/booking';

const BOOKING_API = 'http://localhost:8090/api/booking/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class BookService {
  
  constructor(private http: HttpClient) { }


  createBooking(bookingDetails:Booking){
    console.log(bookingDetails);
    // let headers =new HttpHeaders();
    // headers.append("Content-Type","application/json");
    // headers.append("Accept","*/*");
    return this.http.post(BOOKING_API,bookingDetails,httpOptions);
  }

  findBookings(searchArray: any) {
    let findBookingHost =BOOKING_API;
    const keys = Object.keys(searchArray);
    findBookingHost=findBookingHost.concat("?search=")
    keys.forEach(key => {
      let value = searchArray[key];
      if((key.includes('pnr')|| key.includes('email')) && value !==null && value !==''){
        findBookingHost = findBookingHost.concat(key).concat(':').concat(value).concat(',');
      }
    })
    console.log('url is ' + findBookingHost);
    return this.http.get(findBookingHost);
  }

  cancelBooking(pnr: string) {
    return this.http.delete(BOOKING_API+'?pnr='+pnr);
  }
  

}
