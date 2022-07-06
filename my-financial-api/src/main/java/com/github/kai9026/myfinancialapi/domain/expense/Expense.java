package com.github.kai9026.myfinancialapi.domain.expense;

import com.github.kai9026.myfinancialapi.domain.Money;
import com.github.kai9026.myfinancialapi.domain.StandardDate;
import org.jmolecules.ddd.types.AggregateRoot;

import java.math.BigDecimal;
import java.util.Arrays;

public class Expense implements AggregateRoot<Expense, ExpenseId> {

  private static final String ERROR_DATES = "End date must be later than start date.";

  private static final String ERROR_TYPE = "The type introduced is invalid.";

  private ExpenseId id;

  private StandardDate startDate;

  private StandardDate endDate;

  private StandardDate billingDate;

  private Money totalValue;

  private ExpenseType expenseType;

  public StandardDate startDate() {
    return startDate;
  }

  public StandardDate endDate() {
    return endDate;
  }

  public StandardDate billingDate() {
    return billingDate;
  }

  public Money totalValue() {
    return totalValue;
  }

  public ExpenseType expenseType() {
    return expenseType;
  }

  private Expense(StandardDate startDate, StandardDate endDate,
                  StandardDate billingDate, Money totalValue,
                  ExpenseType expenseType) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.billingDate = billingDate;
    this.totalValue = totalValue;
    this.expenseType = expenseType;
  }

  public void setStartDate(StandardDate startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(StandardDate endDate) {
    this.endDate = endDate;
  }

  public void setTotalValue(Money totalValue) {
    this.totalValue = totalValue;
  }

  public void setExpenseType(ExpenseType expenseType) {
    this.expenseType = expenseType;
  }

  @Override
  public ExpenseId getId() {
    return id;
  }

  public static Expense createExpense(String start, String end, String billing,
      BigDecimal total, String type) {
    final var startDate = StandardDate.createDate(start);
    final var endDate = StandardDate.createDate(end);
    final var billingDate = StandardDate.createDate(billing);
    if (endDate.date().isBefore(startDate.date())) {
      throw new IllegalArgumentException(ERROR_DATES);
    }
    if (Arrays.stream(ExpenseType.values())
            .noneMatch(eType -> eType.name().equals(type.toUpperCase()))) {
      throw new IllegalArgumentException(ERROR_TYPE);
    }
    var expenseValue = Money.createCurrency(total);
    return new Expense(startDate, endDate, billingDate, expenseValue,
      ExpenseType.valueOf(type.toUpperCase()));
  }
}
