import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { NgForm } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class AirportService {


  host:string = "http://localhost:8090/api/airport";
  constructor(private http:HttpClient) { }

  findAllAirports(){
    return this.http.get(this.host.concat('?search=status:1'));
  }

}
