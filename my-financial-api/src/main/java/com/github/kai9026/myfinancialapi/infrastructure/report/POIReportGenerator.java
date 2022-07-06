package com.github.kai9026.myfinancialapi.infrastructure.report;

import com.github.kai9026.myfinancialapi.application.exception.ReportException;
import com.github.kai9026.myfinancialapi.application.port.SheetProviderService;
import com.github.kai9026.myfinancialapi.domain.Money;
import com.github.kai9026.myfinancialapi.domain.expense.Expense;
import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ApplicationScoped
public class POIReportGenerator implements SheetProviderService {

  public static final String EXPENSES_REPORT_FILE = "/tmp/expenses.xlsx";

  private static final Integer TITLE_HEADER_ROW = 1;

  private static final Integer COLUMNS_HEADER_ROW = 4;

  private static final Integer EXPENSES_ROW = 7;

  private static final Short INTERVAL_COLOR = IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex();

  private static final Short BILLING_COLOR = IndexedColors.LIGHT_GREEN.getIndex();

  private static final Short TOTAL_VALUE_COLOR = IndexedColors.CORAL.getIndex();

  private static final Short TOTAL_PERSON_COLOR = IndexedColors.TAN.getIndex();

  private static final Short EXPENSE_TYPE_COLOR = IndexedColors.LIGHT_TURQUOISE.getIndex();

  private static final Short CAPTION_COLOR = IndexedColors.GREY_25_PERCENT.getIndex();

  @Override
  public File generateSheetReport(TreeMap<Month, List<Expense>> dataExpenses) {

    var sheetFile = new File(EXPENSES_REPORT_FILE);
    try (OutputStream fileOut = new FileOutputStream(sheetFile);
         Workbook book = new XSSFWorkbook()) {

      for (Map.Entry<Month, List<Expense>> entryMonth: dataExpenses.entrySet()) {
        final var monthKey = entryMonth.getKey();
        final var sheet = book
                .createSheet(monthKey.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES")).toUpperCase());

        this.createTitle(book, sheet, monthKey);
        this.createColumnsHeader(book, sheet);
        final var expenses = entryMonth.getValue();
        this.insertExpensesData(book, sheet, expenses);
      }

      book.write(fileOut);
      return sheetFile;
    } catch (IOException e) {

      throw new ReportException(e.getMessage());
    }

  }

  private void createTitle(Workbook book, Sheet sheet, Month month) {

    var titleHeader = sheet.createRow(TITLE_HEADER_ROW);
    var titleStyle = book.createCellStyle();
    this.setTextCenterAlignment(titleStyle);
    titleStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
    titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    final var titleHeaderCell = titleHeader.createCell(1);
    titleHeaderCell
            .setCellValue("Hoja de gastos de "
                    + month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES")).toUpperCase());
    titleHeaderCell.setCellStyle(titleStyle);

    sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 10));
  }

  private void createColumnsHeader(Workbook book, Sheet sheet) {

    var columnsHeader = sheet.createRow(COLUMNS_HEADER_ROW);

    final var expenseTypeCell = columnsHeader.createCell(1);
    expenseTypeCell.setCellValue("Tipo de gasto");
    final var expenseTypeStyle = book.createCellStyle();
    expenseTypeStyle.setFillForegroundColor(EXPENSE_TYPE_COLOR);
    expenseTypeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    this.setTextCenterAlignment(expenseTypeStyle);
    this.setBold(book, expenseTypeStyle);
    expenseTypeCell.setCellStyle(expenseTypeStyle);

    final var startDateCell = columnsHeader.createCell(3);
    startDateCell.setCellValue("Fecha inicio");
    final var intervalDateStyle = getIntervalDateStyle(book);
    this.setBold(book, intervalDateStyle);
    startDateCell.setCellStyle(intervalDateStyle);

    final var endDateCell = columnsHeader.createCell(5);
    endDateCell.setCellValue("Fecha fin");
    endDateCell.setCellStyle(intervalDateStyle);

    final var billingDateCell = columnsHeader.createCell(7);
    billingDateCell.setCellValue("Fecha facturacion");
    final var billingDateStyle = getBillingDateStyle(book);
    this.setBold(book, billingDateStyle);
    billingDateCell.setCellStyle(billingDateStyle);

    final var totalValueCell = columnsHeader.createCell(9);
    totalValueCell.setCellValue("Total");
    final var totalStyle = getTotalStyle(book, false);
    this.setBold(book, totalStyle);
    totalValueCell.setCellStyle(totalStyle);

    this.groupColumnsHeader(sheet);
  }

  private void groupColumnsHeader(Sheet sheet) {

    var propertyTemplate = new PropertyTemplate();
    for (var i=1; i < 10; i+=2) {
      sheet.addMergedRegion(
        new CellRangeAddress(COLUMNS_HEADER_ROW, COLUMNS_HEADER_ROW + 1, i, i+1
      ));
      propertyTemplate.drawBorders(new CellRangeAddress(
        COLUMNS_HEADER_ROW, COLUMNS_HEADER_ROW + 1, i, i+1),
        BorderStyle.MEDIUM, BorderExtent.HORIZONTAL
      );
    }
    propertyTemplate.applyBorders(sheet);
  }

  private void setTextCenterAlignment(CellStyle cellStyle) {

    cellStyle.setAlignment(HorizontalAlignment.CENTER);
    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
  }

  private CellStyle getIntervalDateStyle(Workbook book) {
    var intervalStyle = book.createCellStyle();
    this.setTextCenterAlignment(intervalStyle);
    intervalStyle.setFillForegroundColor(INTERVAL_COLOR);
    intervalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    return intervalStyle;
  }

  private CellStyle getBillingDateStyle(Workbook book) {
    var billingDateStyle = book.createCellStyle();
    this.setTextCenterAlignment(billingDateStyle);
    billingDateStyle.setFillForegroundColor(BILLING_COLOR);
    billingDateStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    return billingDateStyle;
  }

  private CellStyle getTotalStyle(Workbook book, boolean isTotalPerson) {
    var totalValueStyle = book.createCellStyle();
    this.setTextCenterAlignment(totalValueStyle);
    totalValueStyle.setFillForegroundColor(isTotalPerson ? TOTAL_PERSON_COLOR : TOTAL_VALUE_COLOR);
    totalValueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    return totalValueStyle;
  }

  private CellStyle getCaptionStyle(Workbook book) {

    var captionStyle = book.createCellStyle();
    this.setTextCenterAlignment(captionStyle);
    captionStyle.setFillForegroundColor(CAPTION_COLOR);
    captionStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    return captionStyle;
  }

  private void setBold(Workbook book, CellStyle style) {
    var f = book.createFont();
    f.setBold(true);
    style.setFont(f);
  }

  private void insertExpensesData(Workbook book, Sheet sheet, List<Expense> expenses) {
    var counter = new AtomicInteger(0);
    expenses.forEach(expense -> this.insertExpense(book, expense, sheet, counter));
    this.insertTotals(book, sheet, expenses, counter.get() + EXPENSES_ROW);
  }

  private void insertExpense(Workbook book, Expense expense, Sheet sheet, AtomicInteger counter) {

    final var increment = counter.get();
    final var rowNum = EXPENSES_ROW + increment;
    final var expenseRow = sheet.createRow(rowNum);

    final var expenseTypeCell = expenseRow.createCell(1);
    expenseTypeCell.setCellValue(expense.expenseType().name());
    final var expenseTypeStyle = book.createCellStyle();
    this.setTextCenterAlignment(expenseTypeStyle);
    expenseTypeStyle.setFillForegroundColor(EXPENSE_TYPE_COLOR);
    expenseTypeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    expenseTypeCell.setCellStyle(expenseTypeStyle);

    final var expenseStartDateCell = expenseRow.createCell(3);
    expenseStartDateCell.setCellValue(expense.startDate().toSpanishFormat());
    expenseStartDateCell.setCellStyle(getIntervalDateStyle(book));

    final var expenseEndDateCell = expenseRow.createCell(5);
    expenseEndDateCell.setCellValue(expense.endDate().toSpanishFormat());
    expenseEndDateCell.setCellStyle(getIntervalDateStyle(book));

    final var expenseBillingDateCell = expenseRow.createCell(7);
    expenseBillingDateCell.setCellValue(expense.billingDate().toSpanishFormat());
    expenseBillingDateCell.setCellStyle(getBillingDateStyle(book));

    final var expenseTotalCell = expenseRow.createCell(9);
    expenseTotalCell.setCellValue(expense.totalValue().amount().doubleValue());
    final var totalStyle = getTotalStyle(book, false);
    final var creationHelper = book.getCreationHelper();
    totalStyle.setDataFormat(creationHelper
            .createDataFormat().getFormat("#,##0.00 \"€\"_);(#,##0.00 \"€\")"));
    expenseTotalCell.setCellStyle(totalStyle);

    this.groupExpenseDataRow(sheet, rowNum);

    counter.set(increment + 3);
  }

  private void groupExpenseDataRow(Sheet sheet, int rowNum) {

    var propertyTemplate = new PropertyTemplate();
    for (var i=1; i < 10; i+=2) {
      sheet.addMergedRegion(
        new CellRangeAddress(rowNum, rowNum + 1, i, i+1
      ));
      propertyTemplate.drawBorders(
        new CellRangeAddress(
          rowNum, rowNum + 1, i, i+1),
          BorderStyle.MEDIUM, BorderExtent.BOTTOM
      );
    }
    propertyTemplate.applyBorders(sheet);
  }

  private void insertTotals(Workbook book, Sheet sheet, List<Expense> expenses, int counter) {

    final var totalsRow = sheet.createRow(counter);

    final var totalCell = totalsRow.createCell(9);
    final var totalExpenses = expenses.stream()
            .map(Expense::totalValue)
            .map(Money::amount)
            .mapToDouble(BigDecimal::doubleValue)
            .sum();
    totalCell.setCellValue(totalExpenses);
    final var totalStyle = getTotalStyle(book, false);
    final var creationHelper = book.getCreationHelper();
    totalStyle.setDataFormat(creationHelper
            .createDataFormat().getFormat("#,##0.00 \"€\"_);(#,##0.00 \"€\")"));
    totalCell.setCellStyle(totalStyle);

    final var totalPersonCell = totalsRow.createCell(11);
    final var totalPerson = totalExpenses / 2;
    totalPersonCell.setCellValue(totalPerson);
    final var totalPersonStyle = getTotalStyle(book, true);
    totalPersonStyle.setDataFormat(creationHelper
            .createDataFormat().getFormat("#,##0.00 \"€\"_);(#,##0.00 \"€\")"));
    totalPersonCell.setCellStyle(totalPersonStyle);

    this.groupTotalsDataRow(sheet, counter, false);

    final var captionRow = sheet.createRow(counter + 2);
    final var captionTotalCell = captionRow.createCell(9);
    captionTotalCell.setCellValue(" TOTAL ");
    final var captionTotalStyle = getCaptionStyle(book);
    this.setBold(book, captionTotalStyle);
    captionTotalCell.setCellStyle(captionTotalStyle);

    final var captionTotalPersonCell = captionRow.createCell(11);
    captionTotalPersonCell.setCellValue(" TOTAL PERSONA");
    final var captionTotalPersonStyle = getCaptionStyle(book);
    this.setBold(book, captionTotalPersonStyle);
    captionTotalPersonCell.setCellStyle(captionTotalPersonStyle);

    this.groupTotalsDataRow(sheet, counter + 2, true);
  }

  private void groupTotalsDataRow(Sheet sheet, int row, boolean isTotalPerson) {

    var propertyTemplate = new PropertyTemplate();
    for (var i=9; i < 12; i+=2) {
      sheet.addMergedRegion(
          new CellRangeAddress(row, row + 1, i, i+1
          ));

      if (!isTotalPerson) {
        propertyTemplate.drawBorders(
                new CellRangeAddress(
                        row, row + 1, i, i+1),
                BorderStyle.MEDIUM, BorderExtent.BOTTOM
        );
        propertyTemplate.applyBorders(sheet);
      }
    }
  }
}
