import { Component, OnInit } from '@angular/core';
import { UsersService } from './services/users.service';
import { Observable } from 'rxjs';
import { User } from './models/User';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  user$ = new Observable<User | null>();

  constructor(
    private usersService: UsersService
  ) {}

  ngOnInit(): void {
    this.user$ = this.usersService.user$
    this.usersService.autoLoginLogout();
  }


}
