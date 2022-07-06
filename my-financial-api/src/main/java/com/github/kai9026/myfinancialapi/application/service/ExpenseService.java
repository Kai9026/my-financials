package com.github.kai9026.myfinancialapi.application.service;

import com.github.kai9026.myfinancialapi.application.converter.ExpenseConverter;
import com.github.kai9026.myfinancialapi.application.usecase.ExpenseUseCase;
import com.github.kai9026.myfinancialapi.application.model.dto.ExpenseDTO;
import com.github.kai9026.myfinancialapi.domain.repository.ExpenseRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ExpenseService implements ExpenseUseCase {

  @Inject
  ExpenseRepository expenseRepository;

  @Inject
  ExpenseConverter expenseConverter;

  @Override
  public Uni<Long> createExpense(ExpenseDTO expenseDTO) {

    final var expense = expenseConverter.expenseDTOToExpense(expenseDTO);
    return this.expenseRepository.saveExpense(expense);
  }

  @Override
  public Uni<ExpenseDTO> getExpenseById(Long id) {

    return this.expenseRepository.getExpenseById(id)
      .onItem().ifNotNull().transform(expenseConverter::expenseToDTO);
  }

  @Override
  public Multi<ExpenseDTO> getExpensesFromCurrentMonth(Integer currentMonth) {

    return this.expenseRepository.getExpensesByMonth(currentMonth)
      .onItem().transform(expenseConverter::expenseToDTO);
  }

}
