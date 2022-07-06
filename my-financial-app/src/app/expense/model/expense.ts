export class Expense {

  type: string;

  startDate: string;

  endDate: string;

  billingDate: string;

  total: string;

  constructor(type: string, start: string, end: string, billing: string, total: string) {

    this.type = type;
    this.startDate =  start;
    this.endDate = end;
    this.billingDate = billing;
    this.total = total;
  }

  public get expenseType() {

    return this.type;
  }

  public set expenseType(type: string) {

    this.type = type;
  }

  public get expenseStartDate() {

    return this.startDate;
  }

  public set expenseStartDate(start: string) {

    this.startDate = start;
  }

  public get expenseEndDate() {

    return this.endDate;
  }

  public set expenseEndDate(end: string) {

    this.endDate = end;
  }

  public get expenseBillingDate() {

    return this.billingDate;
  }

  public set expenseBillingDate(billing: string) {

    this.billingDate = billing;
  }

  public get expenseTotal() {

    return this.total;
  }

  public set expenseTotal(total: string) {

    this.total = total;
  }
}
