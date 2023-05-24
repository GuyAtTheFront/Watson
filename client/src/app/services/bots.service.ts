import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, PipeTransform } from '@angular/core';
import { Bot } from '../models/models';
import { BehaviorSubject, firstValueFrom, Observable, of, Subject } from 'rxjs';
import { debounceTime, delay, switchMap, tap } from 'rxjs/operators';
import { DecimalPipe } from '@angular/common';

interface SearchResult {
	bots: Bot[];
	total: number;
}

interface State {
	searchTerm: string;
}

// const compare = (v1: string | number, v2: string | number) => (v1 < v2 ? -1 : v1 > v2 ? 1 : 0);

function matches(bot: Bot, term: string, pipe: PipeTransform) {
	return (
		bot.username.toLowerCase().includes(term.toLowerCase())
	);
}

@Injectable({
  providedIn: 'root'
})
export class BotsService{

  private _loading$ = new BehaviorSubject<boolean>(true);
	private _search$ = new Subject<void>();
	private _bots$ = new BehaviorSubject<Bot[]>([]);
	private _total$ = new BehaviorSubject<number>(0);

	private _state: State = {
		searchTerm: '',
	};

  private bots: Bot[] = [];

  constructor( private httpClient: HttpClient, 
              private pipe: DecimalPipe ) {
	
    this.getBots().subscribe(x => {this.bots = x});

		this._search$
			.pipe(
				tap(() => this._loading$.next(true)),
				debounceTime(200),
				switchMap(() => this._search()),
				delay(200),
				tap(() => this._loading$.next(false)),
			)
			.subscribe((result) => {
				this._bots$.next(result.bots);
				this._total$.next(result.total);
			});

		this._search$.next();
	}

  async getBotById(id: number) : Promise<Bot>{
    return firstValueFrom(this.httpClient.get<Bot>(`/api/bots/${id}`));
  }

  public addBot(bot: Bot) {
    this.httpClient.post(`/api/bots`, bot)
      .subscribe(() => {
        this.getBots().subscribe(x => {
          this.bots = x;
          this._search$.next();
        });
      });
  }

  public deleteBot(id: number) {
    this.httpClient.delete(`/api/bots/${id}`)
      .subscribe(() => {
        this.getBots().subscribe(x => {
          this.bots = x; 
          this._search$.next()
        });                    
      });
  }

  get bots$() {
		return this._bots$.asObservable();
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

  private getBots() {
    return this.httpClient.get<Bot[]>("/api/bots");
  }

  private _set(patch: Partial<State>) {
		Object.assign(this._state, patch);
		this._search$.next();
	}

  private _search(): Observable<SearchResult> {
		const { searchTerm } = this._state;

		// 1. sort
		let bots = this.bots;
		// bots.push(    {
		// 	imgUrl: "https://robohash.org/61273521ww2.png",
		// 	username: "KukuBot",
		// 	id: 2127352129
		// })

		// 2. filter
		bots = bots.filter((bot) => matches(bot, searchTerm, this.pipe));
		const total = bots.length;

		// 3. paginate
		// countries = countries.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
		return of({ bots, total });
	}
  
}
