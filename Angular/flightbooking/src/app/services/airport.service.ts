import { HttpClient, HttpHeaders } from '@angular/common/http';
import {  Injectable, Output } from '@angular/core';
import { Params } from '@angular/router';
import { Airport } from '../models/airport';

const AIRPORT_API = 'http://localhost:8090/api/airport';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AirportService {

  constructor(private http:HttpClient) { }

  findAllActiveAirports(){
    let host="http://localhost:8090/api/booking/airport"
    return this.http.get(host.concat('?search=status:1'));
  }

  findAllAirports(params:Params){
    let url="";
    const keys = Object.keys(params);
    keys.forEach(key => {
      let value = params[key];
      if (value!==null || value!=='') {
          url = url.concat(key).concat(':').concat(value).concat(',');
        }
      }
    )
    return this.http.get(AIRPORT_API.concat('?search=').concat(url));
  }

  findAirportById(id: string) {
    return this.http.get(AIRPORT_API.concat('?search=id:' + id));
  }

  addAirport(airportData: Airport) {
    return this.http.post(AIRPORT_API, airportData, httpOptions);
  }

  updateAirport(airportData: Airport) {
    return this.http.put(AIRPORT_API, airportData, httpOptions);
  }

  
  getIdFromLocation(airporArray: Airport[],locationName:string){
    let returnArray = airporArray.filter((x) => {
      return (locationName === x.airportLocation) 
    });
    return returnArray[0].airportId;
  }

}
