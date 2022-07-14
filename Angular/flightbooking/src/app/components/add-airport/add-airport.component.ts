import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Airport } from 'src/app/models/airport';
import { AirportService } from 'src/app/services/airport.service';
import { CommonService } from 'src/app/services/common.service';
import { first } from 'rxjs/operators';


@Component({
    selector: 'app-add-airport',
    templateUrl: './add-airport.component.html',
    styleUrls: ['./add-airport.component.css']
})
export class AddAirportComponent implements OnInit {

    form!: FormGroup;
    id!: string;
    isAddMode!: boolean;
    submitted = false;
    errorMessage: string = "";
    airportList: Airport[] = [];
    statusArray: any[] = [];

    constructor(private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private airportService: AirportService,
        private commonService:CommonService) { }

    ngOnInit(): void {
        this.statusArray = this.commonService.getStatusArray();
        this.id = this.route.snapshot.params['id'];
        this.isAddMode = !this.id;

        this.form = this.formBuilder.group({
            airportName: ['', Validators.required],
            airportLocation: ['', Validators.required],
            airportCode: ['', Validators.required],
            status: ['', Validators.required]

        });

        if (!this.isAddMode) {
            this.airportService.findAirportById(this.id)
                  .pipe(first())
                .subscribe(
                    (data: any) => {
                        this.form.patchValue({
                            airportName: data[0].airportName,
                            airportLocation: data[0].airportLocation,
                            airportCode: data[0].airportCode,
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
            this.createAirport();
        } else {
            this.updateAirport();
        }
    }
    private createAirport() {
        this.airportService.addAirport(this.form.value)
            .subscribe({
                next: (res: any) => {
                    console.log(res);
                    this.airportList = res;
                    this.router.navigate(['/admin/airport/']);
                },
                error: (e) => {
                    console.log(e);
                    this.errorMessage = e.message;
                }
            });
    }

    private updateAirport() {
        this.form.addControl('airportId', new FormControl(this.id));
        this.airportService.updateAirport(this.form.value)
            .subscribe({
                next: (res: any) => {
                    console.log(res);
                    this.airportList = res;
                    this.router.navigate(['/admin/airport/']);

                },
                error: (e) => {
                    console.log(e);
                    this.errorMessage = e.message;
                }
            });
    }
}
