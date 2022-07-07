import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AirportService {

  host:string = "http://localhost:8090/api/airport";
  constructor(private http:HttpClient) { }

  findAllAirports(){
    return this.http.get(this.host);
  }

}
