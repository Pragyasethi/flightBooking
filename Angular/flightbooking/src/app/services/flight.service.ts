import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Params } from '@angular/router';
import { Flight } from '../models/flight';


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
  
  /*for user view */
  searchActiveFlights(params: Params) {
    let host = FLIGHT_API+"?departureDate=";
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
    return this.http.get(host.concat(searchUrl).concat('status:1'));

  }

  findAllActiveFlights(){
    return this.http.get(FLIGHT_API.concat('?search=status:1'));
  }

  findAllFlights(params: Params) {
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

  findFlightById(id: string) {
    return this.http.get(FLIGHT_API.concat('?departureDate=&search=id:' + id));
  }

  addFlight(flightData: Flight) {
    return this.http.post(FLIGHT_API, flightData, httpOptions);
  }

  updateFlight(flightData: Flight) {
    return this.http.put(FLIGHT_API, flightData, httpOptions);
  }

}
