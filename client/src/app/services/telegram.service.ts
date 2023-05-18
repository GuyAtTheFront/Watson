import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TelegramUser } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class TelegramService {

  constructor( private httpClient: HttpClient ) {}

  validateToken(token: string) {
    const params = new HttpParams().append("token", token);
    return this.httpClient.get<{result: TelegramUser}>(`https://api.telegram.org/bot${token}/getMe`, {params});
  }
}
