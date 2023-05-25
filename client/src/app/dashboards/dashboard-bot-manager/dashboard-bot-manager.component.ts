import { Component, Input, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
// import { NgbdModalContent } from 'src/app/dev/modals/modals.component';
import { VerifyBotComponent } from './modals/verify-bot/verify-bot.component';
import { TelegramService } from 'src/app/services/telegram.service';
import { AddBotComponent } from './modals/add-bot/add-bot.component';
import { Bot, TelegramUser } from 'src/app/models/models';
import { Router } from '@angular/router';
import { BotsService } from 'src/app/services/bots.service';

@Component({
  selector: 'app-dashboard-bot-manager',
  templateUrl: './dashboard-bot-manager.component.html',
  styleUrls: ['./dashboard-bot-manager.component.css'],
})
export class DashboardBotManagerComponent implements OnInit {

	bots$!: Observable<Bot[]>;
	total$!: Observable<number>;
  modalRef!: NgbModalRef;

  token: string = "";
  isTokenValid: boolean = true;

  constructor( public service: BotsService,
            private telegramService: TelegramService,
            private modalService: NgbModal,
            private router: Router ) {

	}

  ngOnInit(): void {
    this.bots$ = this.service.bots$;
		this.total$ = this.service.total$;
    console.log("init init init")
  }

  openVerify() {
		this.modalRef = this.modalService.open(VerifyBotComponent);
		this.modalRef.componentInstance.name = 'verify';
    this.modalRef.componentInstance.isValidToken = true;
    this.modalRef.result.then(res => {if(res) {this.validateToken(res)}});
	}

  openAdd() {
    this.modalRef = this.modalService.open(AddBotComponent);
		this.modalRef.componentInstance.name = 'add';
    // this.modalRef.result.then((botData:Bot) => {console.log(botData)});
    this.modalRef.result.then((botData:Bot) => {if(botData) {this.addBot(botData)}});
  }

  private validateToken(token: string) {
    this.telegramService.validateToken(token).subscribe({
      next: res => this.validToken(res, token),
      // next: res => console.log(res),
      error: err => this.invalidToken(err, token)
    })
  }

  private validToken(res: {result: TelegramUser}, token: string) {
    // open add
    this.openAdd();
    // pass value to modal
    this.modalRef.componentInstance.formData = res.result;
    this.modalRef.componentInstance.token = token;

  }

  private invalidToken(err: any, token: string) {
    this.openVerify();
    this.modalRef.componentInstance.isValidToken = false;
    this.modalRef.componentInstance.token = token;
  }

  private addBot(botData: Bot) {

    // TODO: Add to helper class?
    botData.imageUrl = `https://robohash.org/${botData.id}.png`
    // Add bots to list
    this.service.addBot(botData);
    // check bots in list
  }

  launchBot(botId: number) {
    // const origin = window.location.origin;
    // const url = `${origin}/chat`
    // window.open(url, '_blank')
    this.router.navigate(['bot', botId]);
  }

  deleteBot (id: number) {
    this.service.deleteBot(id);
  }

}

// this.botForm.setValue({token: token, id: res.result.id, username: res.result.username})