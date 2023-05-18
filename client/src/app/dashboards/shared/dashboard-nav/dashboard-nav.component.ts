import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-dashboard-nav',
  templateUrl: './dashboard-nav.component.html',
  styleUrls: ['./dashboard-nav.component.css']
})
export class DashboardNavComponent implements OnInit{

  active = "";

  constructor( private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.url.subscribe(x => this.active = x[0].path);
  }

}
