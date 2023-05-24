export class User {
    constructor(
        private id: number, 
        private username: string, 
        private expiry: number, 
        private token: string
        ) {}

    getToken() {
        return this.token;
    }
}