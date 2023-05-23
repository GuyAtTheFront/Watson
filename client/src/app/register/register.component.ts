import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UsersService } from '../services/users.service';
import { tap, firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import { ToastService } from '../services/toast.service';

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
    private router: Router,
    private toastService: ToastService) {}

  async onSignUp(form: NgForm) {

    var isValid = true;
    const regex = new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    
    // Validate Email Pattern
    if (!regex.test(form.value.email)) {
      console.log("invalid email pattern");
      isValid = false;
      this.toastService.showDanger("That email.. looks sus");
    }

    // Validate Password Length
    if ((form.value.password as string).trim().length < 7) {
      console.log("Password must be 7 or more characters")
      isValid = false;
      this.toastService.showDanger("Your password must be 7 or more characters long");
    }

    // Validate Passord matches
    if (form.value.password !== form.value.confirmPassword) {
      console.log("Password does not match")
      isValid = false;
      this.toastService.showDanger("Did you make a typi? Your password does not match");
    }

    // Check email against server
    let exists = await firstValueFrom(this.usersService.usernameExists(form.value.email));

    if (exists) {
        console.log("email exists")
          this.toastService.showDanger("This email address already has an account");

          isValid = false;
    }

    if(!isValid) {
          console.log("not valid")
      // Don't post form data
      return;
    }

    // Post credential to server to create account
    await firstValueFrom(this.usersService.registerUser(form.value.email, form.value.password))
            .then(() => {
              form.reset();
              this.router.navigate(['login']);
              this.toastService.clear();
              this.toastService.showSuccess("Account Created!");
            })
            .catch(() => {
              this.toastService.showDanger("Oops.. an error as occured")
            })

    return;
  }
}
