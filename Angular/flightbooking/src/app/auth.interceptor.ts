import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
  
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { StoreTokenService } from './services/store-token.service';

const TOKEN_HEADER_KEY = 'Authorization';       // for Spring Boot back-end

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private tokenService: StoreTokenService) { }

  //   intercept() gets HTTPRequest object, change it and forward to HttpHandler object’s handle() method. 
  //It transforms HTTPRequest object into an Observable<HttpEvents>.
  // next: HttpHandler object represents the next interceptor in the chain of interceptors.
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let authReq = request;
    console.log("In interceptor: "+request)
    const token = this.tokenService.getToken();
    console.log("In interceptor: "+token)

    if (token != null) {
      authReq = request.clone({ headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
    }
    return next.handle(authReq);
  }
}
