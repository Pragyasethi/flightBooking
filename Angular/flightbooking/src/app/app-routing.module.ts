import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookFlightComponent } from './components/book-flight/book-flight.component';
import { BookingDetailsComponent } from './components/booking-details/booking-details.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginComponent } from './components/login/login.component';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';
import { RegisterComponent } from './components/register/register.component';
import { SearchBookingComponent } from './components/search-booking/search-booking.component';
import { SearchFlightComponent } from './components/search-flight/search-flight.component';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, //prefix,full
  { path: 'flights', component: HomepageComponent },
  { path: 'flights/search', component: SearchFlightComponent },
  { path: 'bookflight', component: BookFlightComponent },
  { path: 'bookings/search', component: SearchBookingComponent },
  { path: 'home', component: WelcomePageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', component: PagenotfoundComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [HomepageComponent, SearchFlightComponent, PagenotfoundComponent];
