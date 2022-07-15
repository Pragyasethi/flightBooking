import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { Airline } from 'src/app/models/airline';
import { Airport } from 'src/app/models/airport';
import { Flight } from 'src/app/models/flight';
import { AirlineService } from 'src/app/services/airline.service';
import { AirportService } from 'src/app/services/airport.service';
import { CommonService } from 'src/app/services/common.service';
import { FlightService } from 'src/app/services/flight.service';

@Component({
  selector: 'app-add-flight',
  templateUrl: './add-flight.component.html',
  styleUrls: ['./add-flight.component.css']
})
export class AddFlightComponent implements OnInit {
  form!: FormGroup;
  id!: string;
  isAddMode!: boolean;
  submitted = false;
  errorMessage: string = "";
  flightList: Flight[] = [];
  airportList: Airport[] = [];
  airlineList: Airline[] = [];
  statusArray: any[] = [];
  scheduledForArray: any[] = [];

  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private flightService: FlightService, private commonService: CommonService,
    private airportService: AirportService,
    private airlineService: AirlineService) { }

  ngOnInit(): void {
    this.statusArray = this.commonService.getStatusArray();
    this.scheduledForArray = this.commonService.getSchedulerForArray();
    this.id = this.route.snapshot.params['id'];
    this.isAddMode = !this.id;

    this.form = this.formBuilder.group({
      flightNumber: ['', Validators.required],
      airlineId: ['', Validators.required],
      source: ['', Validators.required],
      destination: ['', Validators.required],
      price: ['', Validators.required],
      capacity: ['', Validators.required],
      scheduledFor: ['', Validators.required],
      arrivalTime: ['', Validators.required],
      departureTime: ['', Validators.required],
      status: ['', Validators.required]
    });

    this.findAllActiveAirlines();
    this.findAllActiveAirports();


    if (!this.isAddMode) {
      this.flightService.findFlightById(this.id)
        .pipe(first())
        .subscribe(
          (data: any) => {
            console.log(data);
            this.form.patchValue({
              flightNumber: data[0].flightNumber,
              price: data[0].price,
              capacity: data[0].capacity,
              airlineId: this.airlineService.getIdFromName(this.airlineList, data[0].airlineName),
              source: this.airportService.getIdFromLocation(this.airportList, data[0].source),
              destination: this.airportService.getIdFromLocation(this.airportList, data[0].destination),
              scheduledFor: this.commonService.getScheduleForArrayFromString(data[0].scheduledfor),
              departureTime: data[0].departureTime,
              arrivalTime: data[0].arrivalTime,
              status: this.commonService.getStatusIdFromName(data[0].status)
            });
          });
    }

  }
  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }

    if (this.isAddMode) {
      this.createAirline();
    } else {
      this.updateAirline();
    }
  }
  private createAirline() {
    console.log(this.form.value['scheduledFor']);
    this.form.setControl('scheduledfor'
    , new FormControl(this.commonService.getStringFromScheduleForArray(this.form.value['scheduledFor'])));
    this.flightService.addFlight(this.form.value)
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.flightList = res;
          this.router.navigate(['/admin/flight/']);
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      });
  }

  private updateAirline() {
    this.form.setControl('scheduledfor'
      , new FormControl(this.commonService.getStringFromScheduleForArray(this.form.value['scheduledFor'])));
    this.form.addControl('flightId', new FormControl(this.id));
    this.flightService.updateFlight(this.form.value)
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.flightList = res;
          this.router.navigate(['/admin/flight/']);
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      });
  }

  findAllActiveAirlines() {
    this.airlineService.findAllActiveAirlines()
      .subscribe({
        next: (res: any) => {
          this.airlineList = res;
          console.log(this.airlineList);
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      })
  }

  findAllActiveAirports() {
    this.airportService.findAllActiveAirports()
      .subscribe({
        next: (res: any) => {
          this.airportList = res;
          console.log(this.airlineList);
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      })
  }

}
