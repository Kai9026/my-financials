package com.github.kai9026.myfinancialapi.infrastructure.db.query;

public final class ExpenseQueries {

  public static final String GET_EXPENSE_BY_ID =
    "SELECT * FROM expenses WHERE id = $1";

  public static final String INSERT_EXPENSE =
    "INSERT INTO expenses (start_date, end_date, billing_date, total, type) "
    + "VALUES ($1, $2, $3, $4, $5)"
    + "RETURNING id";

  public static final String GET_EXPENSES_BY_MONTH =
    "SELECT * FROM expenses WHERE billing_date BETWEEN $1 AND $2";

  public static final String GET_EXPENSES_UNTIL_NOW =
    "SELECT * FROM expenses WHERE billing_date BETWEEN $1 AND $2 ORDER BY billing_date ASC";

  private ExpenseQueries() {}
}
