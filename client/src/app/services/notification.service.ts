import { Injectable } from '@angular/core';
import { BotsService } from './bots.service';
import { ChatUsersService } from './chat-users.service';
import { ChatMessagesService } from './chat-messages.service';
import { RxStompService } from './rx-stomp.service';
import { Subscription, map, tap } from 'rxjs';
import { ChatMessage, ChatUser } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(
    private chatUserService: ChatUsersService,
    private chatMessageService: ChatMessagesService,
    private rxStompService: RxStompService
  ) {}

  subToMemberDetails(botId: number) : Subscription {
    return this.rxStompService
            .watch({destination: `/topic/memberDetails/${botId}`})
            .pipe(
              map(iMessage => JSON.parse(iMessage.body) as ChatUser),
              tap(chatUser => this.chatUserService.updateUser(chatUser)))
            .subscribe();
  }


  subToChatMessages(botId: number, userId: number) : Subscription {
    return this.rxStompService
            .watch({destination: `/topic/chatMessages/${botId}/${userId}`})
            .pipe(
              map(iMessage => JSON.parse(iMessage.body) as ChatMessage),
              tap(chatMessage => this.chatMessageService.updateChatMessage(chatMessage)))
            .subscribe();
  }
}
