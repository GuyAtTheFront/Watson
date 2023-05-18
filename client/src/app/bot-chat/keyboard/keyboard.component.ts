import { Component, EventEmitter, Output } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-keyboard',
  templateUrl: './keyboard.component.html',
  styleUrls: ['./keyboard.component.css']
})
export class KeyboardComponent {

  model = {message: ""};

  @Output()
  sentMessage = new EventEmitter<string>();

  sendMessage(form: NgForm) {
    this.sentMessage.emit(form.value.message)
    form.reset();
  }
}
