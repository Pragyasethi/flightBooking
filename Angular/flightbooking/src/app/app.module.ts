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
import { HttpClientModule } from '@angular/common/http';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { AirportService } from './services/airport.service';
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
    MatAutocompleteModule,
    MatDialogModule, MatDividerModule
  ],
  providers: [

    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
