import { Component, Inject, OnInit } from '@angular/core';
import { Booking } from '../../models/booking';
import { FormArray, FormBuilder, FormGroup, FormControl, Validators, NgForm } from '@angular/forms';
import { BookService } from '../../services/book.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';


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


  constructor(private formBuilder: FormBuilder,
    private bookService: BookService, private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(
      params => this.params = params);
    this.form = this.formBuilder.group({
      noOfSeats: new FormControl([0], [Validators.required]),
      passengerDetails: new FormArray([]),
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
    if (length > this.passengerDetails.length) {
      const size = length - this.passengerDetails.length;
      for (let i = 0; i < size; i++) {
        this.addPassenger();
      }
    } else if (length < this.passengerDetails.length) {
      const size = this.passengerDetails.length - length;
      for (let i = 0; i < size; i++) {
        this.removePassenger();
      }
    }
  }

  addPassenger(): void {
    this.passengerDetails.push(
      new FormGroup({
        passengerName: new FormControl(),
        idProofNumber: new FormControl(),
        age: new FormControl(),
        gender: new FormControl()
      })
    );
  }

  removePassenger() {
    const index = this.passengerDetails.length - 1;
    this.passengerDetails.removeAt(index);
  }

  get passengerDetails(): FormArray {
    return this.form.get('passengerDetails') as FormArray;
  }

  onSubmit() {
    this.submitted = true;
    this.saveBooking(this.form);
  }

  saveBooking(bookingForm: FormGroup) {
    this.bookService.createBooking(bookingForm.value).
      subscribe({
        next: (res: any) => {
          console.log(res);
          this.router.navigate(['/bookings/search'],
            { queryParams: { pnr: res['pnr'] } }
          );
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      })
  }
}
