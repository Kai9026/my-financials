import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { CreateExpenseFormComponent } from './create-expense-form/create-expense-form.component';
import { SharedModule } from '../shared/shared.module';
import { ListExpensesMonthComponent } from './list-expenses-month/list-expenses-month.component';



@NgModule({
  declarations: [
    CreateExpenseFormComponent,
    ListExpensesMonthComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'en-UK'},
    {provide: DatePipe}
  ]
})
export class ExpenseModule { }
