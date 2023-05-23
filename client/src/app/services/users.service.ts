import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private httpClient: HttpClient) { }

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
    return this.httpClient.post<{"token": string}>("/api/login", body);
  }
}


