/* eslint-disable class-methods-use-this */
/* eslint-disable @typescript-eslint/no-unused-vars */
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ExpenseDto } from 'src/app/expense/model/expense_dto';
import { environment } from 'src/environments/environment';
import { Expense } from '../../expense/model/expense';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  private apiUrl: string = environment.apiUrl;

  createExpense(expense: Expense): Observable<Object> {

    return this.saveExpense(expense);
  }

  private saveExpense(expense: Expense) {

    const headers = new HttpHeaders({
      'Access-Control-Allow-Origin': '*'
    })
    return this.http.post(this.apiUrl, expense, {headers});
  }

  getExpensesFromCurrentMonth(): Observable<ExpenseDto[]> {

    const currentMonth = new Date().getMonth() + 1;
    return this.http.get<ExpenseDto[]>(this.apiUrl.concat(`?month=${currentMonth}`))
      .pipe(map(data => data));
  }

  constructor(private http: HttpClient) { }
}
