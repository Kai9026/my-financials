package com.github.kai9026.myfinancialapi.domain.repository;

import com.github.kai9026.myfinancialapi.domain.expense.Expense;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.time.Month;
import java.util.List;
import java.util.TreeMap;

public interface ExpenseRepository {

  Uni<Expense> getExpenseById(Long id);

  Uni<Long> saveExpense(Expense expense);

  Multi<Expense> getExpensesByMonth(int month);

  TreeMap<Month, List<Expense>> getExpensesUntilNow();
}
