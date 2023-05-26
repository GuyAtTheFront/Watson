export class User {
    constructor(
        private _id: number, 
        private _username: string, 
        private _expiry: number, 
        private _token: string
        ) {}

    get token() {
        return this._token;
    }

    get id() {
        return this._id
    }

    get username() {
        return this._username;
    }

    get expiry() {
        return this._expiry;
    }
}