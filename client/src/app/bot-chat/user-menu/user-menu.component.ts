import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { CHAT_USERS } from 'src/app/dev/chatUsers.data';
import { ChatUser } from 'src/app/models/models';
import { ChatUsersService } from 'src/app/services/chat-users.service';

@Component({
  selector: 'app-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.css']
})
export class UserMenuComponent implements OnInit {

  @Input()
  activatedUserId: number | null = null;

  @Output()
  selectUser = new EventEmitter<number>();

	chatUsers$!: Observable<ChatUser[]>;
	total$!: Observable<number>;

  constructor( 
    public chatUsersService: ChatUsersService 
  ) {}
  
  ngOnInit(): void {
    this.chatUsers$ = this.chatUsersService.chatUsers$;
    this.total$ = this.chatUsersService.total$;
  }

  onSelectUser(id: number) {
    this.selectUser.emit(id);
  }

}
