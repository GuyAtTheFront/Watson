import { DecimalPipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable, PipeTransform } from '@angular/core';
import { BehaviorSubject, Observable, Subject, debounceTime, delay, firstValueFrom, of, switchMap, tap } from 'rxjs';
import { ChatMessage } from '../models/models';

interface SearchResult {
	messages: ChatMessage[];
	total: number;
}

interface State {
	searchTerm: string;
}

function matches(message: ChatMessage, term: string, pipe: PipeTransform) {
	return (
		message.content.toString().toLowerCase().includes(term.toLowerCase())
	);
}

@Injectable({
  providedIn: 'root'
})
export class ChatMessagesService {

  private _loading$ = new BehaviorSubject<boolean>(true);
	private _search$ = new Subject<void>();
	private _messages$ = new BehaviorSubject<ChatMessage[]>([]);
	private _total$ = new BehaviorSubject<number>(0);

	private _state: State = {
		searchTerm: '',
	};

  private messages: ChatMessage[] = [];

  constructor( private httpClient: HttpClient, 
    private pipe: DecimalPipe ) {
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
      this._messages$.next(result.messages);
      this._total$.next(result.total);
    });

    this._search$.next();
}

  public refreshChatMessagesByIds(botId: number, memberId: number) : void {
    this.httpClient.get<ChatMessage[]>(`/api/messages/${botId}/${memberId}`)
      .subscribe(res => {
                          this.messages = res; 
                          this._search$.next();
                          console.dir(this.messages)
                        });  
  }

  public updateChatMessage(update: ChatMessage): void {
    // push new record to top of the list
    this.messages.push(update);
    this._search$.next();
  }

  public sendMessage(body: {memberId: number, botId: number, message: string }) {
    return this.httpClient.post("/api/messages",body);
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

  get messages$() {
		return this._messages$.asObservable();
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
		let messages = this.messages;
		// bots.push(    {
		// 	imgUrl: "https://robohash.org/61273521ww2.png",
		// 	username: "KukuBot",
		// 	id: 2127352129
		// })

		// 2. filter
		messages = messages.filter((msg) => matches(msg, searchTerm, this.pipe));
		const total = messages.length;

		// 3. paginate
		// countries = countries.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
		return of({ messages, total });
	}

}

