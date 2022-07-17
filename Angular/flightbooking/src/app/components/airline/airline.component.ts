import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Airline } from 'src/app/models/airline';
import { AirlineService } from 'src/app/services/airline.service';
import { AirlineDetailsComponent } from '../airline-details/airline-details.component';

@Component({
  selector: 'app-airline',
  templateUrl: './airline.component.html',
  styleUrls: ['./airline.component.css']
})
export class AirlineComponent implements OnInit {

  airlineList: Airline[] = [];
  errorMessage: string = "";
  params!: Params;

  constructor(private router: Router, private airlineService: AirlineService,
    private route: ActivatedRoute,public dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(
      params => this.params = params);
    this.findAll(this.params);
  }
  openDialog(airlineDetails: Airline) {
    this.dialog.open(AirlineDetailsComponent, { data: airlineDetails });
  }
  findAll(params: any) {
    this.airlineService.findAllAirlines(params)
      .subscribe({
        next: (res: any) => {
          this.airlineList = res;
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      })
  }
  editAirline(id: any) {
    this.router.navigate(['/admin/airline/edit/'+id]);

  }


}
