import { Injectable, OnDestroy, PipeTransform } from '@angular/core';
import { ChatUser } from '../models/models';
import { BehaviorSubject, Observable, Subject, debounceTime, delay, firstValueFrom, of, switchMap, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { DecimalPipe } from '@angular/common';
import { NotificationService } from './notification.service';

interface SearchResult {
	chatUsers: ChatUser[];
	total: number;
}

interface State {
	searchTerm: string;
}

function matches(chatUser: ChatUser, term: string, pipe: PipeTransform) {
	return (
		chatUser.id.toString().toLowerCase().includes(term.toLowerCase())
	);
}

@Injectable({
  providedIn: 'root'
})

export class ChatUsersService {

  private _loading$ = new BehaviorSubject<boolean>(true);
	private _search$ = new Subject<void>();
	private _chatUsers$ = new BehaviorSubject<ChatUser[]>([]);
	private _total$ = new BehaviorSubject<number>(0);

	private _state: State = {
		searchTerm: '',
	};

  private chatUsers: ChatUser[] = [];

  constructor( 
    private httpClient: HttpClient, 
    private pipe: DecimalPipe
    ) {

    // this.getChatUsersById(botId).subscribe(x => {this.chatUser = x});

		this._search$
			.pipe(
				tap(() => this._loading$.next(true)),
				debounceTime(200),
				switchMap(() => this._search()),
				delay(200),
				tap(() => this._loading$.next(false)),
			)
			.subscribe((result) => {
				this._chatUsers$.next(result.chatUsers);
				this._total$.next(result.total);
			});

		this._search$.next();
	}

  public refreshChatUsersById(botId: number) : void {
    this.httpClient.get<ChatUser[]>(`/api/members/${botId}`)
        .subscribe(res => {
          this.chatUsers = res;
		  this._search$.next();
          });  
  }

  public updateUser(update: ChatUser): void {
    const found = this.chatUsers.findIndex( x => x.id == update.id);
    if(found >= 0) {
      // Remove old record
      this.chatUsers.splice(found, 1);
    }
    // push new record to top of the list
    this.chatUsers.unshift(update);
    this._search$.next();
  }

  // public addBot(bot: Bot) {
  //   this.httpClient.post(`/api/bots`, bot)
  //     .subscribe(() => {
  //       this.getBots().subscribe(x => {
  //         this.bots = x;
  //         this._search$.next();
  //       });
  //     });
  // }

  // public deleteBot(id: number) {
  //   this.httpClient.delete(`/api/bots/${id}`)
  //     .subscribe(() => {
  //       this.getBots().subscribe(x => {
  //         this.bots = x; 
  //         this._search$.next()
  //       });                    
  //     });
  // }

  get chatUsers$() {
		return this._chatUsers$.asObservable();
	}
	get total$() {
		return this._total$.asObservable();
	}
	get loading$() {
		return this._loading$.asObservable();
	}
  get searchTerm() {
		return this._state.searchTerm;
	}

  set searchTerm(searchTerm: string) {
		this._set({ searchTerm });
	}

  // private getBots() {
  //   return this.httpClient.get<Bot[]>("/api/bots");
  // }

  private _set(patch: Partial<State>) {
		Object.assign(this._state, patch);
		this._search$.next();
	}

  private _search(): Observable<SearchResult> {
		const { searchTerm } = this._state;

		// 1. sort
		let chatUsers = this.chatUsers;
		// bots.push(    {
		// 	imgUrl: "https://robohash.org/61273521ww2.png",
		// 	username: "KukuBot",
		// 	id: 2127352129
		// })

		// 2. filter
		chatUsers = chatUsers.filter((bot) => matches(bot, searchTerm, this.pipe));
		const total = chatUsers.length;

		// 3. paginate
		// countries = countries.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
		return of({ chatUsers, total });
	}

}

