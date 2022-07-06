import { DatePipe, formatDate, Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder , Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SnackbarComponent } from 'src/app/shared/component/snackbar/snackbar.component';
import { ExpenseService } from '../../shared/service/expense.service';
import { Expense } from '../model/expense';


@Component({
  templateUrl: './create-expense-form.component.html',
  styleUrls: ['./create-expense-form.component.scss']
})
export class CreateExpenseFormComponent {
  expenseForm = this.fb.group({

    type: ['', Validators.required],
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
    billingDate: ['', Validators.required],
    total: ['', Validators.required]
  });

  isProcessing : boolean = false;

  get type() {

    return this.expenseForm.get('type');
  }

  get startDate() {

    return this.expenseForm.get('startDate');
  }

  get endDate() {

    return this.expenseForm.get('endDate');
  }

  get billingDate() {

    return this.expenseForm.get('billingDate');
  }

  get total() {

    return this.expenseForm.get('total');
  }

  constructor(private fb: FormBuilder,
    private service: ExpenseService,
    private dateFormat: DatePipe,
    private router: Router,
    private snackbar: MatSnackBar,
    private location: Location) { }

  create() {

    this.isProcessing = true;
    const expenseToSave = this.mapToExpense();
    this.service.createExpense(expenseToSave).subscribe(
      res => {
        this.isProcessing = false;
        localStorage.setItem('expense-created', '1');
        this.router.navigate(['']);
        console.log(res)
      },
      err => {
        this.isProcessing = false;
        this.snackbar.openFromComponent(SnackbarComponent, {
          duration: 7000,
          data: '❌ ❌ Error creando el gasto ❌ ❌'
        });
        console.error(err)
      }
    );
  }

  mapToExpense(): Expense {

      const type = this.expenseForm.controls.type.value;

      const startDate = this.expenseForm.controls.startDate.value;
      const start = formatDate(startDate, 'yyyy-MM-dd', 'en-UK');

      const endDate = this.expenseForm.controls.endDate.value;
      const end = formatDate(endDate, 'yyyy-MM-dd', 'en-UK');

      const billingDate = this.expenseForm.controls.billingDate.value;
      const billing = formatDate(billingDate, 'yyyy-MM-dd', 'en-UK');

      const total = this.expenseForm.controls.total.value;

      return new Expense(type, start, end, billing, total);

  }

  goBack() {

    this.location.back();
  }


}

