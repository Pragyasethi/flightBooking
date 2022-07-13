import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Params } from '@angular/router';


const FLIGHT_API = 'http://localhost:8090/api/booking/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  host: string = "http://localhost:8090/api/booking/flight?departureDate=";
  searchUrl: string = '&search=';
  departureDate: any;
  constructor(private http: HttpClient, private datePipe: DatePipe) { }

  searchActiveFlights(params: Params) {
    const keys = Object.keys(params);
    keys.forEach(key => {
      let value = params[key];
      if (value) {
        if ("date".match(key)) {
          this.departureDate = this.datePipe.transform(value, 'dd-MM-yyyy');
          this.host = this.host.concat(this.departureDate);
          value = this.datePipe.transform(value, 'EEEE');
          this.searchUrl = this.searchUrl.concat('scheduledfor').concat('~').concat(value).concat(',');
        } else {
          this.searchUrl = this.searchUrl.concat(key).concat(':').concat(value).concat(',');
        }
      }
    })
    console.log('url is ' + this.searchUrl);
    return this.http.get(this.host.concat(this.searchUrl).concat('status:1'));

  }
}
