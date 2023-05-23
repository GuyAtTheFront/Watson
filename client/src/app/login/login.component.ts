import { Component } from '@angular/core';
import { UsersService } from '../services/users.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { firstValueFrom } from 'rxjs'

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
    private router: Router ) {}

  async onLogin(form: NgForm) {
    // Post credential to server to create account
      let auth = await firstValueFrom(this.usersService.loginUser(form.value.username, form.value.password))
                            .catch(x=> {console.log("error")});
      console.log(auth);
      // TODO: if error
        // Toast "An error has occured"

    // save auth
    sessionStorage.setItem("user", JSON.stringify(auth));
    // form.reset();

    // this.router.navigate(['dashboard']);

    // Toast "Account successfully created!"
    console.log("done")
    return;
  }

}
