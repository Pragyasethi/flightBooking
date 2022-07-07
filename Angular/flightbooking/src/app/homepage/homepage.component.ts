import { Component, OnInit } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { Observable } from 'rxjs';
import { Airport } from '../models/airport';
import { AirportService } from '../services/airport.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  myControl = new FormControl('');
  filteredOptions!: Observable<String[]>;


  errorMessage: string = "";
  airportList: Airport[] = [];

  constructor(private airportService: AirportService) { }

  ngOnInit(): void {
    this.findAllAirports();
  }

  // private _filter(value: string): Airport[] {
  //   const filterValue = value.toLowerCase();

  //   return this.airportList.filter(airport => airport.airportLocation.toLowerCase().includes(filterValue));
  // }

  findAllAirports() {
    this.airportService.findAllAirports()
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.airportList = res;
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      })
  }

  // title = 'angular-mat-select-app';

  // selected: string="Select";

  // currencies = [
  //   { value: 'us', text: 'U.S. Dollar $' },
  //   { value: 'euro', text: 'Euro €' },
  //   { value: 'yen', text: 'Japanese Yen ¥' },
  //   { value: 'pound', text: 'Pounds £' },
  //   { value: 'inr', text: 'Indian Rupee ₹' }
  // ];

  // submitted = false;

  // onSubmit(form: NgForm) {
  //   this.submitted = true;
  //   console.log('Your form data : ', form.value);
  // }
}
