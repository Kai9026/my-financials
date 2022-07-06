package com.github.kai9026.myfinancialapi.domain.expense;

import org.jmolecules.ddd.types.Identifier;

public class ExpenseId implements Identifier {

  private final Long id;

  private ExpenseId(Long id) {
    this.id = id;
  }

  public Long id() {
    return id;
  }

  public static ExpenseId of(Long id) {
    return new ExpenseId(id);
  }
}
