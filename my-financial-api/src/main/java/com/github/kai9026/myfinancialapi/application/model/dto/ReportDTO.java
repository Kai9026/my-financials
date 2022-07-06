package com.github.kai9026.myfinancialapi.application.model.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@RegisterForReflection
public class ReportDTO {

  private String title;

  private String resume;

  private Month month;

  private List<ExpenseDTO> expenses;

  public ReportDTO(String title, String resume, Month month, List<ExpenseDTO> expenses) {
    this.title = title;
    this.resume = resume;
    this.month = month;
    this.expenses = expenses;
  }

  public ReportDTO() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getResume() {
    return resume;
  }

  public void setResume(String resume) {
    this.resume = resume;
  }

  public Month getMonth() {
    return month;
  }

  public void setMonth(Month month) {
    this.month = month;
  }

  public List<ExpenseDTO> getExpenses() {
    return expenses;
  }

  public void setExpenses(List<ExpenseDTO> expenses) {
    this.expenses = expenses;
  }

  public int getMonthNumber() {
    return this.month.getValue();
  }

  public String getMonthValue() {
    return this.month.getDisplayName(TextStyle.FULL, Locale.getDefault()).toUpperCase();
  }

  public int getPrevious() {
    return this.month.getValue() - 1;
  }

  public int getNext() {
    return this.month.getValue() + 1;
  }

  public boolean isEmpty() {
    return this.expenses.isEmpty();
  }
}
