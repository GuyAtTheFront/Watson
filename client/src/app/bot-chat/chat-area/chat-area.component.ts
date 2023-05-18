import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ChatMessage } from 'src/app/models/models';
import { ChatMessagesService } from 'src/app/services/chat-messages.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-chat-area',
  templateUrl: './chat-area.component.html',
  styleUrls: ['./chat-area.component.css']
})
export class ChatAreaComponent implements OnInit{

  botId!: number;

	messages$: Observable<ChatMessage[]>;
	total$: Observable<number>;

  constructor( 
    public chatMessageService: ChatMessagesService,
    private notificationService: NotificationService,
    private route: ActivatedRoute
    ){

    this.messages$ = chatMessageService.messages$;
    this.total$ = chatMessageService.total$;
  }
  ngOnInit(): void {
    this.route.params.subscribe(x => this.botId = x['id']);
  }


}
