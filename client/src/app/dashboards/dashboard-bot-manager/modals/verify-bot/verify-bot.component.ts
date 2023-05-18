import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TelegramService } from 'src/app/services/telegram.service';

@Component({
  selector: 'app-verify-bot',
  templateUrl: './verify-bot.component.html',
  styleUrls: ['./verify-bot.component.css']
})
export class VerifyBotComponent implements OnInit {

  botForm!: FormGroup;

	@Input() 
  name!: string;

  @Input()
  isValidToken!: boolean;

  @Input()
  token = "6127352122:AAF8vvpa9VO6IaHtPQsEyG8Srp2z6ywxpsg";

  constructor(private fb: FormBuilder,
              public activeModal: NgbActiveModal) {}

  ngOnInit(): void {
    this.botForm = this.createForm();
  }

    createForm() {
      let grp = this.fb.group({
          token: this.fb.control<string>(this.token),
          username: this.fb.control<string>(""),
          id: this.fb.control<number>(0)
      })
      return grp;
    }

    processForm() {
        const tokenValue = (this.botForm.value as {token: string}).token
        this.activeModal.close(tokenValue)
        }
}
