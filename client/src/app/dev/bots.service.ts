/* eslint-disable @typescript-eslint/adjacent-overload-signatures */
import { Injectable, PipeTransform } from '@angular/core';

import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

// import { Country } from './country';
// import { COUNTRIES } from './countries';
import { DecimalPipe } from '@angular/common';
import { debounceTime, delay, switchMap, tap } from 'rxjs/operators';
import { BOTS } from './bots.data';
// import { SortColumn, SortDirection } from './sortable.directive';

interface Bot {
    imgUrl: string;
    username: string;
    id: number;
}


interface SearchResult {
	bots: Bot[];
	total: number;
}

interface State {
	searchTerm: string;
}

const compare = (v1: string | number, v2: string | number) => (v1 < v2 ? -1 : v1 > v2 ? 1 : 0);

// function sort(countries: Country[], column: SortColumn, direction: string): Country[] {
// 	if (direction === '' || column === '') {
// 		return countries;
// 	} else {
// 		return [...countries].sort((a, b) => {
// 			const res = compare(a[column], b[column]);
// 			return direction === 'asc' ? res : -res;
// 		});
// 	}
// }

function matches(bot: Bot, term: string, pipe: PipeTransform) {
	return (
		bot.username.toLowerCase().includes(term.toLowerCase())
	);
}

@Injectable({ providedIn: 'root' })
export class BotsService {
	private _loading$ = new BehaviorSubject<boolean>(true);
	private _search$ = new Subject<void>();
	private _bots$ = new BehaviorSubject<Bot[]>([]);
	private _total$ = new BehaviorSubject<number>(0);

	private _state: State = {
		searchTerm: '',
	};

	constructor(private pipe: DecimalPipe) {
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

	get bots$() {
		return this._bots$.asObservable();
	}
	get total$() {
		return this._total$.asObservable();
	}
	get loading$() {
		return this._loading$.asObservable();
	}
	// get page() {
	// 	return this._state.page;
	// }
	// get pageSize() {
	// 	return this._state.pageSize;
	// }
	get searchTerm() {
		return this._state.searchTerm;
	}

	// set page(page: number) {
	// 	this._set({ page });
	// }
	// set pageSize(pageSize: number) {
	// 	this._set({ pageSize });
	// }
	set searchTerm(searchTerm: string) {
		this._set({ searchTerm });
	}
	// set sortColumn(sortColumn: SortColumn) {
	// 	this._set({ sortColumn });
	// }
	// set sortDirection(sortDirection: SortDirection) {
	// 	this._set({ sortDirection });
	// }

	private _set(patch: Partial<State>) {
		Object.assign(this._state, patch);
		this._search$.next();
	}

	private _search(): Observable<SearchResult> {
		const { searchTerm } = this._state;

		// 1. sort
		let bots = BOTS;
		bots.push(    {
			imageUrl: "https://robohash.org/61273521ww2.png",
			username: "KukuBot",
			id: 2127352129
		})

		// 2. filter
		bots = bots.filter((bot) => matches(bot, searchTerm, this.pipe));
		const total = bots.length;

		// 3. paginate
		// countries = countries.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
		return of({ bots, total });
	}
}