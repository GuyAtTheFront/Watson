import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {

  constructor(
    private httpClient: HttpClient,
    private toastService: ToastService
  ) {

  }

  onFeedback(feedbackForm: NgForm) {
    console.log(feedbackForm.value);


    var isValid = true;
    const regex = new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    
    // Validate required
    if(
      (feedbackForm.value.name as string).trim().length <= 0 ||
      (feedbackForm.value.email as string).trim().length <= 0 ||
      (feedbackForm.value.content as string).trim().length <= 0
    ) {
      // console.log("blank blank blank!")
      isValid = false;
      this.toastService.showWarning("huh, some fields seem to be blank");
    }

    // Validate Email Pattern
    if (!regex.test(feedbackForm.value.email)) {
      // console.log("invalid email pattern");
      isValid = false;
      this.toastService.showDanger("That email.. looks sus");
    }

    if(!isValid) {
      // console.log("not valid")
      // Don't post form data
      return;
    }

    this.httpClient.post("/api/feedback", feedbackForm.value)
      .subscribe(res => {
        this.toastService.showSuccess("Your email has been sent!");
        feedbackForm.reset();
      }) 

  }
}
