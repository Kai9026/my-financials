package com.github.kai9026.myfinancialapi.application.model.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ExpenseDTO {

  private String type;
  private String startDate;
  private String endDate;
  private String billingDate;
  private String total;

  public ExpenseDTO() {}

  public ExpenseDTO(String type, String startDate, String endDate,
      String billingDate, String total) {
    this.type = type;
    this.startDate = startDate;
    this.billingDate = billingDate;
    this.endDate = endDate;
    this.total = total;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getBillingDate() {
    return billingDate;
  }

  public void setBillingDate(String billingDate) {
    this.billingDate = billingDate;
  }

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }
}
