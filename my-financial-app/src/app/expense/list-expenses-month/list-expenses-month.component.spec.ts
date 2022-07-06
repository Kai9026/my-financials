import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListExpensesMonthComponent } from './list-expenses-month.component';

describe('ListExpensesMonthComponent', () => {
  let component: ListExpensesMonthComponent;
  let fixture: ComponentFixture<ListExpensesMonthComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListExpensesMonthComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListExpensesMonthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
