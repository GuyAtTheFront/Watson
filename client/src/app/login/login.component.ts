import { Component } from '@angular/core';
import { UsersService } from '../services/users.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { firstValueFrom } from 'rxjs'
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  model = {
    username: "",
    password: "",
  }

  constructor ( 
    private usersService: UsersService,
    private router: Router,
    private toastService : ToastService ) {}

  async onLogin(form: NgForm) {
    // Post credential to server to create account
      let auth = await firstValueFrom(this.usersService.loginUser(form.value.username, form.value.password))
                            .then( payload => {
                              sessionStorage.setItem("userToken", payload.token);
                              form.reset();
                              this.router.navigate(['dashboard']);
                              this.toastService.clear();
                              this.toastService.showPrimary("Hello User");
                            });
                            // .catch(x=> {
                            //   this.toastService.showDanger("Username and Password does not match");
                            //   console.log(x);
                            // });

    return;
  }

}
