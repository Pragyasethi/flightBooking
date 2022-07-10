import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { SearchFlightComponent } from './search-flight/search-flight.component';

const routes: Routes = [
  // { path: '', component: HomepageComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }, //prefix,full
  { path: 'home', component: HomepageComponent },
  { path: 'search', component: SearchFlightComponent },
  { path: '**', component: PagenotfoundComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [HomepageComponent, SearchFlightComponent, PagenotfoundComponent];
