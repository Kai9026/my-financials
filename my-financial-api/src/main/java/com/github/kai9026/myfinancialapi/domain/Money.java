package com.github.kai9026.myfinancialapi.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public final class Money {

  private static final String ERROR_REQUIRED = "Amount is required";

  private static final Integer DECIMAL_PLACES = 2;

  private final BigDecimal amount;

  private static final Currency CURRENCY = Currency.getInstance("EUR");
  private static final RoundingMode ROUNDING_MODE = RoundingMode.UP;

  private Money(BigDecimal money) {
    this.amount = money;
  }

  public BigDecimal amount() {
    return amount;
  }

  public static Money createCurrency(BigDecimal amount) {
    final var money = Objects.requireNonNull(amount, ERROR_REQUIRED)
        .setScale(DECIMAL_PLACES, ROUNDING_MODE);
    return new Money(money);
  }

  public String getCurrencyCode() {
    return CURRENCY.getCurrencyCode();
  }

  public String toString() {
    return amount.toString();
  }
}
