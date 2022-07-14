import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StoreTokenService } from 'src/app/services/store-token.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  username?: string;
  constructor(public router:Router,private tokenService: StoreTokenService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenService.getUser();
      this.roles = user.roles;
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.username = user.username;
    }
    
  }
  logout(): void {
    this.tokenService.signOut();
    window.location.reload();
  }

}
