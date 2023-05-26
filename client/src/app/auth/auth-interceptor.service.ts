import { Injectable } from "@angular/core";
import { UsersService } from "../services/users.service";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable, exhaustMap, take } from "rxjs";

@Injectable()
  export class AuthInterceptorService implements HttpInterceptor{
  
    constructor(
      private userService : UsersService,
    //   private toastService: ToastService,
    //   private router: Router,
    ) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        // return next.handle(req);
      
        if(req.url.match(".*/login") || req.url.match(".*/api/users.*")) {
            return next.handle(req);
        }
        
        return this.userService.user$.pipe(
            take(1), 
            exhaustMap(user => {
                let authReq = req.clone({
                    setHeaders: {"Authorization": `Bearer ${user?.token}`}
                    });
                return next.handle(authReq);                
                }));
    }
  }