import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UsersService } from '../services/users.service';
import { tap, firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  model = {
    email: "",
    password: "",
    confirmPassword: ""
  }

  showError = {
    email: false,
    password: false,
    confirmPassowrd: false
  }

  constructor ( 
    private usersService: UsersService,
    private router: Router ) {}

  async onSignUp(form: NgForm) {

    var isValid = true;
    const regex = new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    
    // Validate Email Pattern
    if (!regex.test(form.value.email)) {
      console.log("invalid email pattern");
      isValid = false;
      // toast "Looks like your email is invalid."
    }

    // Validate Password Length
    if ((form.value.password as string).trim().length < 7) {
      console.log("Password must be 7 or more characters")
      isValid = false;
      // toast "Your password must be 7 or more characters long"
    }

    // Validate Passord matches
    if (form.value.password !== form.value.confirmPassword) {
      console.log("Password does not match")
      isValid = false;
      // toast "Did you make a typi? Your password does not match."
    }

    // Check email against server
    let exists = await firstValueFrom(this.usersService.usernameExists(form.value.email));

    if (exists) {
        console.log("email exists")
          // toast "This email address already has an account"
          isValid = false;
    }

    if(!isValid) {
          console.log("not valid")
      // Don't post form data
      return;
    }

    // Post credential to server to create account
    await firstValueFrom(this.usersService.registerUser(form.value.email, form.value.password));
      // TODO: if error
        // Toast "An error has occured"

    form.reset();

    this.router.navigate(['login']);

    // Toast "Account successfully created!"
    console.log("done")
    return;
  }
}
