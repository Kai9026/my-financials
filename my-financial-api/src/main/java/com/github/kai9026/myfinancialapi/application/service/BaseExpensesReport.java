package com.github.kai9026.myfinancialapi.application.service;

import com.github.kai9026.myfinancialapi.domain.expense.Expense;
import com.github.kai9026.myfinancialapi.domain.repository.ExpenseRepository;
import io.smallrye.mutiny.Multi;

import java.time.Month;
import java.util.List;
import java.util.TreeMap;

public class BaseExpensesReport {

  private ExpenseRepository expenseRepository;

  public BaseExpensesReport(final ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
  }

  public BaseExpensesReport() {

  }

  protected Multi<Expense> getExpensesFromMonth(Integer month) {

    return this.expenseRepository.getExpensesByMonth(month);
  }

  protected TreeMap<Month, List<Expense>> getExpensesUntilNow() {

    return this.expenseRepository.getExpensesUntilNow();
  }
}
