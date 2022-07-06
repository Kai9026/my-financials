package com.github.kai9026.myfinancialapi.infrastructure.db.mapper.impl;

import com.github.kai9026.myfinancialapi.domain.expense.Expense;
import com.github.kai9026.myfinancialapi.infrastructure.db.mapper.RowMapper;
import io.vertx.mutiny.sqlclient.Row;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExpenseRowMapper implements RowMapper<Expense> {

  @Override
  public Expense convertToDomainObject(Row rowDb) {
    return Expense.createExpense(
      rowDb.getLocalDate("start_date").format(DateTimeFormatter.ISO_DATE),
      rowDb.getLocalDate("end_date").format(DateTimeFormatter.ISO_DATE),
      rowDb.getLocalDate("billing_date").format(DateTimeFormatter.ISO_DATE),
      rowDb.getBigDecimal("total"),
      rowDb.getString("type"));
  }
}
