package com.github.kai9026.myfinancialapi.application.service;

import com.github.kai9026.myfinancialapi.application.converter.ExpenseConverter;
import com.github.kai9026.myfinancialapi.application.port.SheetProviderService;
import com.github.kai9026.myfinancialapi.application.usecase.ExpensesReportUseCase;
import com.github.kai9026.myfinancialapi.domain.repository.ExpenseRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;

@ApplicationScoped
public class ExpensesReportSheetService extends BaseExpensesReport {

  private SheetProviderService sheetProviderService;

  @Inject
  public ExpensesReportSheetService(ExpenseRepository expenseRepository,
    final SheetProviderService sheetProviderService,
    final ExpenseConverter expenseConverter) {

    super(expenseRepository);
    this.sheetProviderService = sheetProviderService;
  }

  public ExpensesReportSheetService() {

    super();
  }

  public File generateReportInProgress() {

    final var expensesFromMonth = super.getExpensesUntilNow();
    return sheetProviderService.generateSheetReport(expensesFromMonth);
  }
}
