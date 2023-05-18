import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardPasswordManagerComponent } from './dashboard-password-manager.component';

describe('DashboardPasswordManagerComponent', () => {
  let component: DashboardPasswordManagerComponent;
  let fixture: ComponentFixture<DashboardPasswordManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardPasswordManagerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardPasswordManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
