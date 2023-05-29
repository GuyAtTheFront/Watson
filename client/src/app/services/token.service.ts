import { Injectable } from '@angular/core';
import { User } from '../models/User';

interface JwtClaims {
  userId: number;
  username: string;
  exp: number;
}

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  private parse(jwt: string) : JwtClaims {

    var base64Url = jwt.split('.')[1];
    var base64 = base64Url.replace('-', '+').replace('_', '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    // console.log(JSON.parse(jsonPayload));

    return JSON.parse(jsonPayload);

  }

  timeToExpiry(token: string): number {
    const exp = this.parse(token).exp * 1000;
    return exp - new Date().getTime(); 
  }

  isExpired(token: string) : boolean {
    return this.timeToExpiry(token) <= 0;
  }

  toUser(token: string): User | null {

    if (this.isExpired(token)) {
      return null;
    }

    const claims = this.parse(token);
    return new User(claims.userId, claims.username, claims.exp, token);
  }

  invalidate(): void {
    sessionStorage.removeItem("userToken");
  }

  getToken(): string | null {
    return sessionStorage.getItem("userToken");
  }

  setToken(token: string) : void {
    sessionStorage.setItem("userToken", token);
  }

}
