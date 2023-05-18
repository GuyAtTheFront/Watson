import { Component, Input } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
	selector: 'ngbd-modal-content',
	standalone: true,
	// templateUrl: '../../dashboards/dashboard-bot-manager/modals/verify-bot/verify-bot.component.html'
    template: ""
})

export class NgbdModalContent {
	@Input() 
    name: any;

	constructor(public activeModal: NgbActiveModal) {


    }
}

