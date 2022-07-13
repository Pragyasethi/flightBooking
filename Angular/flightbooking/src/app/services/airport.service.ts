import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { NgForm } from '@angular/forms';

const AIRPORT_API = 'http://localhost:8090/api/booking/airport';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AirportService {


  constructor(private http:HttpClient) { }

  findAllAirports(){
    return this.http.get(AIRPORT_API.concat('?search=status:1'));
  }

}
