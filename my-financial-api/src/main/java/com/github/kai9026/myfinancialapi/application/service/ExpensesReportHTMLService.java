package com.github.kai9026.myfinancialapi.application.service;

import com.github.kai9026.myfinancialapi.application.converter.ExpenseConverter;
import com.github.kai9026.myfinancialapi.application.model.dto.ReportDTO;
import com.github.kai9026.myfinancialapi.application.usecase.ExpensesReportUseCase;
import com.github.kai9026.myfinancialapi.domain.repository.ExpenseRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Month;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExpensesReportHTMLService extends BaseExpensesReport
    implements ExpensesReportUseCase<ReportDTO> {

  private ExpenseConverter expenseConverter;

  @Inject
  public ExpensesReportHTMLService(final ExpenseRepository expenseRepository,
       final ExpenseConverter expenseConverter) {

    super(expenseRepository);
    this.expenseConverter = expenseConverter;
  }

  public ExpensesReportHTMLService() {}

  @Override
  public ReportDTO generateReportByMonth(Integer month) {

    final var expenses = super.getExpensesFromMonth(month);
    final var expenseDTOList = expenses.subscribe().asStream().map(expenseConverter::expenseToDTO).collect(Collectors.toList());

    return new ReportDTO("Resumen de gastos",
"Gastos del mes de ",
        Month.of(month),
        expenseDTOList);
  }
}
