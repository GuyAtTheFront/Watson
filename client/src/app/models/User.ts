export class User {
    constructor(
        private id: number, 
        private username: string, 
        private expiry: Date, 
        private token: string
        ) {}


}