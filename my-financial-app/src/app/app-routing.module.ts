import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateExpenseFormComponent } from './expense/create-expense-form/create-expense-form.component';
import { ListExpensesMonthComponent } from './expense/list-expenses-month/list-expenses-month.component';
import { MenuComponent } from './main-menu/menu/menu.component';

const routes: Routes = [
  { path: '', component: MenuComponent},
  { path: 'new-expense', component: CreateExpenseFormComponent },
  { path: 'current-expenses', component: ListExpensesMonthComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
