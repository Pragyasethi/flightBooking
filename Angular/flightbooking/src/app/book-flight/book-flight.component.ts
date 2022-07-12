import { Component, OnInit } from '@angular/core';
import { Booking } from '../models/booking';
import { FormArray, FormBuilder, FormGroup, FormControl, Validators, NgForm } from '@angular/forms';
import { BookService } from '../services/book.service';
import { ActivatedRoute, Params, Router } from '@angular/router';


@Component({
  selector: 'app-book-flight',
  templateUrl: './book-flight.component.html',
  styleUrls: ['./book-flight.component.css']
})
export class BookFlightComponent implements OnInit {

  submitted = false;
  form!: FormGroup;
  errorMessage: any;
  params!: Params;
  bookingList: Booking[] = [];


  constructor(private formBuilder: FormBuilder,
    private bookService: BookService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(
      params => this.params = params);
    this.form = this.formBuilder.group({
      passengerCount: new FormControl([0], [Validators.required]),
      passengers: new FormArray([]),
      departureDate: new FormControl(this.params['date']),
      flightId: new FormControl(this.params['id']),
      phone: new FormControl('', [
        Validators.required,
        Validators.maxLength(10),
        Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]),
      email: new FormControl('', [
        Validators.pattern(
          /[a-zA-Z0-9.-_]{1,}@[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,}/
        )
      ])
    });
  }
  inputTotal(length: any) {
    if (length < 0) {
      return;
    }

    length = Math.min(length, 50);
    if (length > this.passengers.length) {
      const size = length - this.passengers.length;
      for (let i = 0; i < size; i++) {
        this.addPassenger();
      }
    } else if (length < this.passengers.length) {
      const size = this.passengers.length - length;
      for (let i = 0; i < size; i++) {
        this.removePassenger();
      }
    }
  }

  addPassenger(): void {
    this.passengers.push(
      new FormGroup({
        passengerName: new FormControl(),
        idProofNumber: new FormControl(),
        age: new FormControl(),
        gender: new FormControl()
      })
    );
  }

  removePassenger() {
    const index = this.passengers.length - 1;
    this.passengers.removeAt(index);
  }

  get passengers(): FormArray {
    return this.form.get('passengers') as FormArray;
  }

  onSubmit() {
    this.submitted = true;
    this.saveBooking(this.form);
  }

  saveBooking(bookingForm: FormGroup) {
    this.bookService.createBooking(this.form.value).
      subscribe({
        next: (res: any) => {
          console.log(res);
          this.findBookingDetails(res);
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;

        }
      })

  }
  findBookingDetails(res: any) {
    this.bookService.findBookings(res)
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.bookingList=res;
          this.router.navigate(['/book']);
          localStorage.clear();
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;

        }
      })
  }
}
