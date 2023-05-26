import { ViewportScroller } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {

  constructor(
    private viewportScroller: ViewportScroller,
  ){}


  onClickScroll(sectionId: string) {
    this.viewportScroller.setHistoryScrollRestoration("manual");
    console.log(this.viewportScroller.getScrollPosition());
     this.viewportScroller.scrollToAnchor(sectionId);
     setTimeout(() => console.log(this.viewportScroller.getScrollPosition()), 1000);
     this.viewportScroller.setHistoryScrollRestoration("auto");

  }
}
