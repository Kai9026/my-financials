package com.github.kai9026.myfinancialapi.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public final class StandardDate {

  private static final DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter.ISO_DATE;

  private static final String ERROR_DATE_FORMAT = "The Date is not in the correct format";

  private static final String ERROR_REQUIRED = "Date is required";

  private final LocalDate iso8601Date;

  private StandardDate(LocalDate date) {
    this.iso8601Date = date;
  }

  public LocalDate date() {
    return iso8601Date;
  }

  public static StandardDate createDate(String date) {
    try {
      final var formattedDate = ISO_8601_FORMATTER
          .parse(Objects.requireNonNull(date, ERROR_REQUIRED));
      return new StandardDate(LocalDate.from(formattedDate));
    } catch (DateTimeParseException ex) {
      throw new IllegalArgumentException(ERROR_DATE_FORMAT);
    }
  }

  public String toSpanishFormat() {
    return iso8601Date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }

  public int monthNumber() {
    return iso8601Date.getMonthValue();
  }

  public StandardDate firstDayOfMonth(int monthNumber) {

    return new StandardDate(LocalDate.of(Year.now().getValue(), monthNumber, 1));
  }

  public StandardDate lastDayOfMonth(int monthNumber) {

    final var lastDay = Month.of(monthNumber).length(Year.now().isLeap());
    return new StandardDate(LocalDate.of(Year.now().getValue(), monthNumber, lastDay));
  }

  public static StandardDate today() {

    return new StandardDate(LocalDate.now());
  }

  public boolean isLeapYear() {
    return iso8601Date.isLeapYear();
  }

  public String toString() {
    return iso8601Date.format(ISO_8601_FORMATTER);
  }

}
