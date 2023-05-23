import { Component } from '@angular/core';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css']
})
export class BannerComponent {

	constructor(private toastService: ToastService) {}

	toast(){
		this.toastService.showDanger("oi");
	}

}
