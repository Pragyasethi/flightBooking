import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { SearchFlightComponent } from './components/search-flight/search-flight.component';
import { HeaderComponent } from './components/header/header.component';
import { BackgroundComponent } from './components/background/background.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';
import { DatePipe } from '@angular/common';
import { BookFlightComponent } from './components/book-flight/book-flight.component';
import { MatDialogModule } from '@angular/material/dialog';
import { BookingDetailsComponent } from './components/booking-details/booking-details.component';
import { SearchBookingComponent } from './components/search-booking/search-booking.component';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { MatDividerModule } from '@angular/material/divider';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { AirportComponent } from './components/airport/airport.component';
import { FlightComponent } from './components/flight/flight.component';
import { FlightDetailsComponent } from './components/flight-details/flight-details.component';
import { AuthInterceptor } from './auth.interceptor';
import { AddFlightComponent } from './components/add-flight/add-flight.component';
import { AddAirportComponent } from './components/add-airport/add-airport.component';
import { AddAirlineComponent } from './components/add-airline/add-airline.component';
import { AirportDetailsComponent } from './components/airport-details/airport-details.component';
import { AirlineDetailsComponent } from './components/airline-details/airline-details.component';
import { AirlineComponent } from './components/airline/airline.component';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BackgroundComponent,
    routingComponents,
    PagenotfoundComponent,
    BookFlightComponent,
    BookingDetailsComponent,
    SearchBookingComponent,
    WelcomePageComponent,
    RegisterComponent,
    LoginComponent,
    AirportComponent,
    FlightComponent,
    FlightDetailsComponent,
    HomepageComponent,
    SearchFlightComponent,
    AddFlightComponent,
    AddAirportComponent,
    AddAirlineComponent,
    AirportDetailsComponent,
    AirlineDetailsComponent,
    AirlineComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatNativeDateModule,
    NgxMatSelectSearchModule,
    MatSelectModule,
    MatRadioModule,
    MatIconModule,
    HttpClientModule,
    MatAutocompleteModule, MatButtonModule,
    MatDialogModule, MatDividerModule

  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
