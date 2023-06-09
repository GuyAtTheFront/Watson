import { Component, ViewChild } from '@angular/core';
import { BotsService } from '../services/bots.service';
import { ActivatedRoute } from '@angular/router';
import { Bot } from '../models/models';
import { ChatUsersService } from '../services/chat-users.service';
import { ChatMessagesService } from '../services/chat-messages.service';
import { NotificationService } from '../services/notification.service';
import { Subscription, filter, firstValueFrom, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { UsersService } from '../services/users.service';
import { User } from '../models/User';

@Component({
  selector: 'app-bot-chat',
  templateUrl: './bot-chat.component.html',
  styleUrls: ['./bot-chat.component.css']
})
export class BotChatComponent {

  bot!: Bot;
  user!: User;
  activatedUserId: number | null = null;
  subChatUserNotification!: Subscription;
  subChatMessageNotification!: Subscription;

  constructor( 
    private botService: BotsService,
    private route: ActivatedRoute,
    private chatUserService: ChatUsersService,
    private chatMessageService: ChatMessagesService,
    private notificationService: NotificationService,
    private userService: UsersService
    ) {}

  async ngOnInit(): Promise<void> {

    const botId = this.route.snapshot.params['id'];
    // must know what the current Bot Details are
    await this.botService.getBotById(botId).then(response => this.bot = response);

    // initialize ChatUser[] for child component
    // Cannot do in service constructor because cannot pass params to constructor
    // TODO: Might get sub updates before this finishes loading
    this.chatUserService.refreshChatUsersById(botId);

    // Watch websocket for changes in ChatUsers
    this.subChatUserNotification = this.notificationService.subToMemberDetails(botId);
  }

  selectUser(id: number) {
    this.activatedUserId = id;
    this.chatMessageService.refreshChatMessagesByIds(this.bot.id, id);
    if(this.subChatMessageNotification) {
      this.subChatMessageNotification.unsubscribe();
    }
    this.subChatMessageNotification = this.notificationService.subToChatMessages(this.bot.id, id);
  }

  onSendMessage(sentMessage: string) {
    const body = {
      memberId: this.activatedUserId,
      botId: this.bot.id,
      message: sentMessage
    }
    //@ts-ignore
    this.chatMessageService.sendMessage(body).subscribe();
  }

  ngOnDestroy(): void {
    this.subChatUserNotification.unsubscribe();
    this.subChatMessageNotification.unsubscribe();
  }

}
