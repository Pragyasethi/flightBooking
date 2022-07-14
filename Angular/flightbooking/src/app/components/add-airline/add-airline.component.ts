import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Airline } from 'src/app/models/airline';
import { AirlineService } from 'src/app/services/airline.service';
import { first } from 'rxjs/operators';
import { CommonService } from 'src/app/services/common.service';


@Component({
  selector: 'app-add-airline',
  templateUrl: './add-airline.component.html',
  styleUrls: ['./add-airline.component.css']
})
export class AddAirlineComponent implements OnInit {

  form!: FormGroup;
  id!: string;
  isAddMode!: boolean;
  submitted = false;
  errorMessage: string = "";
  airlineList: Airline[] = [];
  statusArray: any[] = [];

  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private airlineService: AirlineService, private commonService: CommonService
  ) { }

  ngOnInit(): void {
    this.statusArray = this.commonService.getArray();
    this.id = this.route.snapshot.params['id'];
    this.isAddMode = !this.id;

    this.form = this.formBuilder.group({
      airlineName: ['', Validators.required],
      airlineLogo: ['', Validators.required],
      status: ['', Validators.required]
    });

    if (!this.isAddMode) {
      this.airlineService.findAirlineById(this.id)
        .pipe(first())
        .subscribe(
          (data: any) => {
            this.form.patchValue({
              airlineName: data[0].airlineName,
              airlineLogo: data[0].airlineLogo,
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
    this.airlineService.addAirline(this.form.value)
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.airlineList = res;
          this.router.navigate(['/admin/airline/']);
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      });
  }

  private updateAirline() {
    this.form.addControl('airlineId', new FormControl(this.id));
    this.airlineService.updateAirline(this.form.value)
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.airlineList = res;
          this.router.navigate(['/admin/airline/']);
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      });
  }

}
