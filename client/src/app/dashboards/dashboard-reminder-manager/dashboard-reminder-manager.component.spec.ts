import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardReminderManagerComponent } from './dashboard-reminder-manager.component';

describe('DashboardReminderManagerComponent', () => {
  let component: DashboardReminderManagerComponent;
  let fixture: ComponentFixture<DashboardReminderManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardReminderManagerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardReminderManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
