/* eslint-disable no-restricted-syntax */
/* eslint-disable no-return-assign */
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ExpenseService } from 'src/app/shared/service/expense.service';
import { ExpenseDto } from '../model/expense_dto';

@Component({
  selector: 'app-list-expenses-month',
  templateUrl: './list-expenses-month.component.html',
  styleUrls: ['./list-expenses-month.component.scss']
})
export class ListExpensesMonthComponent implements OnInit {

  myExpenses: ExpenseDto[] = [];

  currentMonth: string = new Date().toLocaleString('default', { month: 'long' });

  columnsToDisplay: string[] = ['type', 'start', 'end', 'billing', 'total'];

  totalColumns: string[] = ['total'];

  totalCost: number = 0;

  constructor(private service: ExpenseService,
    private location: Location) { }

  ngOnInit(): void {

    this.service.getExpensesFromCurrentMonth().subscribe(data => {
      this.myExpenses = data;
      this.totalCost = data.map(e => Number(e.total)).reduce((acc,sum) => acc + sum, 0);
    });
  }

  goBack() {

    this.location.back();
  }


}
