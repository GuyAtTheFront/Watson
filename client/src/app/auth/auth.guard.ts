import { Injectable } from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { Observable, map, take, tap } from 'rxjs';
import { UsersService } from '../services/users.service';
import { ToastService } from '../services/toast.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard{

  constructor(
    private userService : UsersService,
    private toastService: ToastService,
    private router: Router,
  ) { }

  canActivate():
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
      return this.userService.user$
              .pipe(
                take(1),
                map(user => {
                  return !!user
                }),
                tap(bool => {
                  if(!bool) {
                    this.router.navigate(["/login"]);
                    this.toastService.showWarning("Please login to continue");
                  }
                })
                )

  }
}
