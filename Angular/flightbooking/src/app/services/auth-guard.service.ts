import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { StoreTokenService } from './store-token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private tokenservice: StoreTokenService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

    if (!this.tokenservice.getUser()  || !this.tokenservice.getUser().roles.includes('ROLE_ADMIN')) {
      alert('You are not allowed to view this page. You are redirected to home Page');
      
      this.router.navigate(["/home"], { queryParams: { retUrl: route.url } });
      return false;   
    }
    return true;
  }

}
