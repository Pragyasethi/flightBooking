import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Params } from '@angular/router';


const FLIGHT_API = 'http://localhost:8090/api/flight';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  departureDate: any;
  constructor(private http: HttpClient, private datePipe: DatePipe) { }
  
  searchActiveFlights(params: Params) {
    let host = "http://localhost:8090/api/booking/flight?departureDate=";
    let searchUrl = '&search=';
    const keys = Object.keys(params);
    keys.forEach(key => {
      let value = params[key];
      if (value) {
        if ("date".match(key)) {
          this.departureDate = this.datePipe.transform(value, 'dd-MM-yyyy');
          host = host.concat(this.departureDate);
          value = this.datePipe.transform(value, 'EEEE');
          searchUrl = searchUrl.concat('scheduledfor').concat('~').concat(value).concat(',');
        } else {
          searchUrl = searchUrl.concat(key).concat(':').concat(value).concat(',');
        }
      }
    })
    console.log('url is ' + searchUrl);
    return this.http.get(host.concat(searchUrl).concat('status:1'));

  }

  searchAllFlights(params: Params) {
    let url="";
    const keys = Object.keys(params);
    keys.forEach(key => {
      let value = params[key];
      if (value!==null || value!=='') {
          url = url.concat(key).concat(':').concat(value).concat(',');
        }
      }
    )
    return this.http.get(FLIGHT_API.concat('?departureDate=&search=').concat(url));

  }
}
