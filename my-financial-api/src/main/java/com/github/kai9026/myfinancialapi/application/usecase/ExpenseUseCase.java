package com.github.kai9026.myfinancialapi.application.usecase;

import com.github.kai9026.myfinancialapi.application.model.dto.ExpenseDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface ExpenseUseCase {

  Uni<Long> createExpense(ExpenseDTO expenseDTO);

  Uni<ExpenseDTO> getExpenseById(Long id);

  Multi<ExpenseDTO> getExpensesFromCurrentMonth(Integer currentMonth);
}
