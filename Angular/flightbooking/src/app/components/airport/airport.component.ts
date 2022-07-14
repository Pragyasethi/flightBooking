import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Airport } from 'src/app/models/airport';
import { AirportService } from 'src/app/services/airport.service';
import { AirportDetailsComponent } from '../airport-details/airport-details.component';

@Component({
  selector: 'app-airport',
  templateUrl: './airport.component.html',
  styleUrls: ['./airport.component.css']
})
export class AirportComponent implements OnInit {

  airportList: Airport[] = [];
  errorMessage: string = "";
  params!: Params;

  constructor(private router: Router, private airportService: AirportService,
    private route: ActivatedRoute, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(
      params => this.params = params);
    this.findAll(this.params);
  }
  openDialog(airportDetails: Airport) {
    this.dialog.open(AirportDetailsComponent, { data: airportDetails });
  }

  findAll(params: any) {
    this.airportService.findAllAirports(params)
      .subscribe({
        next: (res: any) => {
          this.airportList = res;
          console.log(this.airportList);
        },
        error: (e) => {
          console.log(e);
          this.errorMessage = e.message;
        }
      })
  }
  editAirport(id: any) {
    this.router.navigate(['/admin/airport/edit/'+id]);

  }

}
