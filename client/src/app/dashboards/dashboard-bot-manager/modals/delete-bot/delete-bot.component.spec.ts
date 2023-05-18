import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteBotComponent } from './delete-bot.component';

describe('DeleteBotComponent', () => {
  let component: DeleteBotComponent;
  let fixture: ComponentFixture<DeleteBotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteBotComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteBotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
