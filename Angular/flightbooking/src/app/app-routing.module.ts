import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddAirlineComponent } from './components/add-airline/add-airline.component';
import { AddAirportComponent } from './components/add-airport/add-airport.component';
import { AddFlightComponent } from './components/add-flight/add-flight.component';
import { AirlineComponent } from './components/airline/airline.component';
import { AirportComponent } from './components/airport/airport.component';
import { BookFlightComponent } from './components/book-flight/book-flight.component';
import { FlightComponent } from './components/flight/flight.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginComponent } from './components/login/login.component';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';
import { RegisterComponent } from './components/register/register.component';
import { SearchBookingComponent } from './components/search-booking/search-booking.component';
import { SearchFlightComponent } from './components/search-flight/search-flight.component';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { AuthGuardService } from './services/auth-guard.service';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, //prefix,full
  { path: 'flight', component: HomepageComponent },
  { path: 'flight/search', component: SearchFlightComponent },
  { path: 'bookflight', component: BookFlightComponent },
  { path: 'bookings/search', component: SearchBookingComponent },
  { path: 'home', component: WelcomePageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin/flight', component: FlightComponent ,canActivate:[AuthGuardService]},
  { path: 'admin/airport', component: AirportComponent ,canActivate:[AuthGuardService]},
  { path: 'admin/airline', component: AirlineComponent ,canActivate:[AuthGuardService]},
  { path: 'admin/flight/edit/:id', component: AddFlightComponent ,canActivate:[AuthGuardService]},
  { path: 'admin/airport/edit/:id', component: AddAirportComponent,canActivate:[AuthGuardService] },
  { path: 'admin/airline/edit/:id', component: AddAirlineComponent ,canActivate:[AuthGuardService]},
  { path: 'admin/flight/add', component: AddFlightComponent,canActivate:[AuthGuardService] },
  { path: 'admin/airport/add', component: AddAirportComponent ,canActivate:[AuthGuardService]},
  { path: 'admin/airline/add', component: AddAirlineComponent ,canActivate:[AuthGuardService]},
  { path: 'admin/home', component: WelcomePageComponent,canActivate:[AuthGuardService] },
  { path: '**', component: PagenotfoundComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [HomepageComponent, SearchFlightComponent,
  PagenotfoundComponent, FlightComponent, LoginComponent, RegisterComponent,
  BookFlightComponent, SearchBookingComponent, WelcomePageComponent, AirlineComponent, AirportComponent, AddAirlineComponent
  , AddAirportComponent, AddFlightComponent];
