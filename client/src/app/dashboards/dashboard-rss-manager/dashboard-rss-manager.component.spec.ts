import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardRssManagerComponent } from './dashboard-rss-manager.component';

describe('DashboardRssManagerComponent', () => {
  let component: DashboardRssManagerComponent;
  let fixture: ComponentFixture<DashboardRssManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardRssManagerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardRssManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
