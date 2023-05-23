import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthInfo } from '../models/models';

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

  loginUser(username: string, password: string) : Observable<AuthInfo> {
    let body = {
      username: username,
      password: password
    }
    return this.httpClient.post<AuthInfo>("/api/login", body);
  }
}


