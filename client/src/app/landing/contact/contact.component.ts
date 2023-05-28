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

    this.httpClient.post("/api/feedback", feedbackForm.value)
      .subscribe(res => {
        this.toastService.showSuccess("Your email has been sent!");
        feedbackForm.reset();
      }) 

  }
}
