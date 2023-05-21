import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

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

  constructor () {}

  onLogin(form: NgForm) {
    const regex = new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    
    // Validate Email Pattern
    if(!regex.test(form.value.email)) {
      console.log("invalid email pattern")
      // return "Looks like your email is invalid."
    }

    // Validate Password Length
    if((form.value.password as string).trim().length < 7) {
      console.log("Password must be 7 or more characters")
      // return "Your password must be 7 or more characters long"
    }

    // Validate Passord matches
    if(form.value.password !== form.value.confirmPassword) {
      console.log("Password does not match")
      // return "Did you make a typi? Your password does not match."
    }

    // Check email against server
      // if exists
          // return "This email address already has an account"

    // Post credential to server to create account
      // if error
        // return "An error has occured"

    // reset form
    // redirect to login page
    // return "Account successfully created!"

    console.log(form.value);
    return;
  }
}
