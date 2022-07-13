import { PrefixNot } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { BookingDetailsComponent } from '../booking-details/booking-details.component';
import { Booking } from '../../models/booking';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-search-booking',
  templateUrl: './search-booking.component.html',
  styleUrls: ['./search-booking.component.css']
})
export class SearchBookingComponent implements OnInit {

  bookingList: Booking[] = [];
  errorMessage: any;
  params!: Params;
  form!: FormGroup;
  isAtleastOnePresent: boolean = false;


  constructor(private formBuilder: FormBuilder, private bookService: BookService, private route: ActivatedRoute,
    private router: Router, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(
      params => this.params = params);

    this.form = this.formBuilder.group({
      pnr: new FormControl(this.params['pnr']),
      email: new FormControl(this.params['email'], [
        Validators.pattern(
          /[a-zA-Z0-9.-_]{1,}@[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,}/
        )
      ])
    });
  }
  findBookingDetails(params: Params) {
    this.bookService.findBookings(params)
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.bookingList = res;
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;

        }
      })
  }
  cancelBooking(pnr:string){
    this.bookService.cancelBooking(pnr)
    .subscribe({
      next: (res: any) => {
        console.log(res);
        this.bookingList = res;
        alert("Ticket has been cancelled.")
      },
      error: (e) => {
        console.log(e);
        this.errorMessage = e.message;

      }
    })
  }

  openDialog(bookingDetails: Booking) {
    this.dialog.open(BookingDetailsComponent, { data: bookingDetails });
  }

  onSubmit() {
    if (this.requireOneControl(this.form)) {
      this.findBookingDetails(this.form.value);
    }else{
      alert("require atleast one input");
    }
  }

  requireOneControl(formGroup: any): boolean {
    if (formGroup.get('pnr').value === '' && formGroup.get('email').value === '') {
      return false;
    } else if (formGroup.get('pnr').value === null && formGroup.get('email').value === null) {
      return false;
    }
    return true;
  }
}
