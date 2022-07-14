import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Params } from '@angular/router';
import { Airline } from '../models/airline';


const AIRLINE_API = 'http://localhost:8090/api/airline';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AirlineService {

  constructor(private http: HttpClient) { }

  findAirlineById(id: string) {
    return this.http.get(AIRLINE_API.concat('?search=id:' + id));
  }

  addAirline(airlineData: Airline) {
    return this.http.post(AIRLINE_API, airlineData, httpOptions);
  }

  updateAirline(airlineData: Airline) {
    return this.http.put(AIRLINE_API, airlineData, httpOptions);
  }

  findAllActiveAirlines(){
    return this.http.get(AIRLINE_API.concat('?search=status:1'));
  }

  findAllAirlines(params:Params){
    let url="";
    const keys = Object.keys(params);
    keys.forEach(key => {
      let value = params[key];
      if (value!==null || value!=='') {
          url = url.concat(key).concat(':').concat(value).concat(',');
        }
      }
    )
    return this.http.get(AIRLINE_API.concat('?search=').concat(url));
  }
}
