package com.github.kai9026.myfinancialapi.application.usecase;

public interface ExpensesReportUseCase<T> {

  T generateReportByMonth(Integer month);
}
