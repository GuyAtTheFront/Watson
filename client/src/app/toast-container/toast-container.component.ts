import { Component, TemplateRef } from '@angular/core';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-toast-container',
  templateUrl: './toast-container.component.html',
  styleUrls: ['./toast-container.component.css'],
  host: { class: 'toast-container position-fixed p-3', style: 'z-index: 1200; 0%; left: 50%; transform: translate(-50%);' },
})
export class ToastContainerComponent {

	constructor(public toastService: ToastService) {}
  
	isTemplate(toast:any) {
		return toast.textOrTpl instanceof TemplateRef;
	}
}
