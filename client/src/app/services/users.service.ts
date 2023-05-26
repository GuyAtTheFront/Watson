import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, take, tap } from 'rxjs';
import { User } from '../models/User';
import { TokenService } from './token.service';
import { Token } from '@angular/compiler';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private _user = new BehaviorSubject<User | null>(null);
  private autoLogoutTimer: any;

  get user$() {
    return this._user.asObservable();
  }

  get currentUser() {
    return this._user.getValue();
  }

  constructor(
    private httpClient: HttpClient,
    private tokenService: TokenService,
    private router: Router) { }

  usernameExists(username: string) : Observable<boolean> {
    let params = new HttpParams().append("username", username);
    return this.httpClient.get<boolean>("/api/users", {params});
  }

  registerUser(username: string, password: string) : Observable<null> {
    let body = {
      username: username,
      password: password
    }
    
    return this.httpClient.post<null>("/api/users", body);
  }

  loginUser(username: string, password: string) : Observable<{"token": string}> {
    let body = {
      username: username,
      password: password
    }
    return this.httpClient
      .post<{"token": string}>("/api/login", body)
      .pipe(
        tap(payload => this.login(payload.token))
        );
  }

  logoutUser() {
    this.logout();
  }

 public autoLoginLogout() {
    const token = this.tokenService.getToken();
    if(!token) {
      return;
    } 
    
    if (this.tokenService.isExpired(token)) {
      this.logout();
      return;
    }

    this.login(token);

    // if(!token || this.tokenService.isExpired(token)) {
    //   this.logout();
    // } else {
    //   this.login(token)
    // };
  }

  private login(token: string) : void {
    this.tokenService.setToken(token);
    let user = this.tokenService.toUser(token);
    this.setLogoutTimer(token);
    this._user.next(user);
  }

  private logout() {
    this.tokenService.invalidate();
    this._user.next(null);
    this.clearLogoutTimer();
    this.router.navigate(['/login'])
  }

 private setLogoutTimer(token: string) {
    if(token) {
      setTimeout(() => {
        this.logout();
      }, this.tokenService.timeToExpiry(token));
    }
  }

 private clearLogoutTimer() {
    this.autoLogoutTimer = null;
  }
}


