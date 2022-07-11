import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  host: string = "http://localhost:8090/api/booking/flight?departureDate=";
  searchUrl: string = '&search=';
  departureDate:any;
  constructor(private http: HttpClient, private datePipe: DatePipe) { }

  searchActiveFlights(searchform: NgForm) {
    const keys = Object.keys(searchform.value);
    keys.forEach(key => {
      let value = searchform.value[key];
      if (value) {
        if ("scheduledfor".match(key)) {
          this.departureDate =this.datePipe.transform(value, 'dd-MM-yyyy');
          localStorage.setItem('date',this.departureDate)
          this.host= this.host.concat(this.departureDate);
          value = this.datePipe.transform(value, 'EEEE');
          this.searchUrl = this.searchUrl.concat(key).concat('~').concat(value).concat(',');
        }else{
        this.searchUrl = this.searchUrl.concat(key).concat(':').concat(value).concat(',');
        }
      }
    })
    console.log('url is ' + this.searchUrl);
    // return this.http.get(this.searchUrl.concat('1:1'));
    return this.http.get(this.host.concat(this.searchUrl).concat('status:1'));

  }
}
