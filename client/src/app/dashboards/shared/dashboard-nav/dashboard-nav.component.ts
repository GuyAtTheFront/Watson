import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { take } from 'rxjs';
import { User } from 'src/app/models/User';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-dashboard-nav',
  templateUrl: './dashboard-nav.component.html',
  styleUrls: ['./dashboard-nav.component.css']
})
export class DashboardNavComponent implements OnInit{

  active = "";
  user: User | null = null;

  constructor(
    private userService: UsersService,
  ) {}

  ngOnInit(): void {
    this.userService.user$
      .pipe(take(1))
      .subscribe(user => this.user = user);
  }

  activate(section: string){
    this.active = section;
  }

}
