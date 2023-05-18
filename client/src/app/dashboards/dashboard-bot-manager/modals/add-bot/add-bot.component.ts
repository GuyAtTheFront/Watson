import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Bot, TelegramUser } from 'src/app/models/models';

@Component({
  selector: 'app-add-bot',
  templateUrl: './add-bot.component.html',
  styleUrls: ['./add-bot.component.css']
})
export class AddBotComponent {

  @Input()
  formData!: TelegramUser;

  @Input()
  name!: string;

  @Input()
  token!: string;

  botForm!: FormGroup;

  constructor(private fb: FormBuilder,
    public activeModal: NgbActiveModal
    ) {}

  ngOnInit(): void {
    this.botForm = this.createForm()
  }

  createForm() {
    let grp = this.fb.group({
        token: this.fb.control<string>(this.token),
        username: this.fb.control<string>(this.formData.username as string),
        id: this.fb.control<number>(this.formData.id)
    })
    return grp;
  }

  processForm() {
      this.activeModal.close(this.botForm.value as Bot);
      }

}
