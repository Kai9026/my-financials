package com.github.kai9026.myfinancialapi.infrastructure.db;

import static com.github.kai9026.myfinancialapi.infrastructure.db.query.ExpenseQueries.GET_EXPENSES_UNTIL_NOW;
import static com.github.kai9026.myfinancialapi.infrastructure.db.query.ExpenseQueries.GET_EXPENSE_BY_ID;
import static com.github.kai9026.myfinancialapi.infrastructure.db.query.ExpenseQueries.GET_EXPENSES_BY_MONTH;
import static com.github.kai9026.myfinancialapi.infrastructure.db.query.ExpenseQueries.INSERT_EXPENSE;

import com.github.kai9026.myfinancialapi.domain.StandardDate;
import com.github.kai9026.myfinancialapi.domain.expense.Expense;
import com.github.kai9026.myfinancialapi.domain.repository.ExpenseRepository;
import com.github.kai9026.myfinancialapi.infrastructure.db.mapper.impl.ExpenseRowMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExpenseRepositoryPostgres implements ExpenseRepository {

  @Inject
  PgPool postgresClient;

  @Inject
  ExpenseRowMapper expenseRowMapper;

  @Override
  public Uni<Expense> getExpenseById(Long id) {
    final Uni<RowSet<Row>> rowSet = postgresClient
      .preparedQuery(GET_EXPENSE_BY_ID)
      .execute(Tuple.of(id));
    return rowSet
      .onItem().transform(RowSet::iterator)
      .onItem().transform(iterator -> iterator.hasNext() ?
        expenseRowMapper.convertToDomainObject(iterator.next()) : null);
  }

  @Override
  public Uni<Long> saveExpense(Expense expense) {
    return postgresClient.preparedQuery(INSERT_EXPENSE)
      .execute(Tuple.of(
          expense.startDate().date(),
          expense.endDate().date(),
          expense.billingDate().date(),
          expense.totalValue().amount(),
          expense.expenseType().name()
      ))
      .onItem().transform(rows -> rows.iterator().next().getLong("id"));
  }

  @Override
  public Multi<Expense> getExpensesByMonth(int monthNumber) {

    final var startDate = StandardDate.today().firstDayOfMonth(monthNumber);
    final var endDate = StandardDate.today().lastDayOfMonth(monthNumber);

    return postgresClient.preparedQuery(GET_EXPENSES_BY_MONTH)
            .execute(Tuple.of(startDate.date(), endDate.date()
            ))
            .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
            .onItem().transform(expenseRowMapper::convertToDomainObject);
  }

  @Override
  public TreeMap<Month, List<Expense>> getExpensesUntilNow() {

    final var firstDayOfYear = LocalDate.of(Year.now().getValue(), Month.JANUARY.getValue(), 1);
    final var toDate = StandardDate.today().lastDayOfMonth(Month.from(LocalDate.now()).getValue());

    final var expenseStream = postgresClient.preparedQuery(GET_EXPENSES_UNTIL_NOW)
      .execute(Tuple.of(firstDayOfYear, toDate.date()
      ))
      .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
      .onItem().transform(expenseRowMapper::convertToDomainObject)
      .subscribe().asStream();

    return expenseStream.collect(
      Collectors.groupingBy(e -> e.billingDate().date().getMonth(),
        TreeMap::new, Collectors.toCollection(ArrayList::new)));
  }

}
