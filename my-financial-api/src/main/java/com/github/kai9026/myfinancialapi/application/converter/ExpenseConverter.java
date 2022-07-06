package com.github.kai9026.myfinancialapi.application.converter;

import com.github.kai9026.myfinancialapi.application.model.dto.ExpenseDTO;
import com.github.kai9026.myfinancialapi.domain.expense.Expense;
import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class ExpenseConverter {

  private static final String DATE_PATTERN_DTO = "dd/MM/yyyy";

  public ExpenseDTO expenseToDTO(Expense expense) {
    return new ExpenseDTO(
      expense.expenseType().name(),
      expense.startDate().date().format(DateTimeFormatter.ofPattern(DATE_PATTERN_DTO)),
      expense.endDate().date().format(DateTimeFormatter.ofPattern(DATE_PATTERN_DTO)),
      expense.billingDate().date().format(DateTimeFormatter.ofPattern(DATE_PATTERN_DTO)),
      expense.totalValue().toString());
  }

  public Expense expenseDTOToExpense(ExpenseDTO expenseDTO) {
    return Expense.createExpense(
      expenseDTO.getStartDate(),
      expenseDTO.getEndDate(),
      expenseDTO.getBillingDate(),
      new BigDecimal(expenseDTO.getTotal()),
      expenseDTO.getType());
  }
}
