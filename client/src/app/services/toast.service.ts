import { Injectable, TemplateRef } from '@angular/core';
import { ToastContainerComponent } from '../toast-container/toast-container.component';

@Injectable({ providedIn: 'root' })
export class ToastService {
	toasts: any[] = [];

	show(textOrTpl: string | TemplateRef<any>, options: any = {}) {
		this.toasts.push({ textOrTpl, ...options });
	}

	remove(toast: any) {
		this.toasts = this.toasts.filter((t) => t !== toast);
	}

	clear() {
		this.toasts.splice(0, this.toasts.length);
	}

	showStandard(text: string) {
		this.show(text);
	}

	showPrimary(text: string) {
		this.show(text, { classname: 'bg-primary text-light', style:'text-align: center;', delay: 10000 });
	}

	showSuccess(text: string) {
		this.show(text, { classname: 'bg-success text-light', style:'text-align: center;', delay: 10000 });
	}

	showWarning(text: string) { 
		this.show(text, { classname: 'bg-warning text-dark', style:'text-align: center;', delay: 10000 });
	}

	showDanger(text: string) {
		this.show(text, { classname: 'bg-danger text-light', style:'text-align: center;',  delay: 10000 });
	}
}