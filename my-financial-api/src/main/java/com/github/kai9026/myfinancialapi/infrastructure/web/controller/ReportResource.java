package com.github.kai9026.myfinancialapi.infrastructure.web.controller;

import com.github.kai9026.myfinancialapi.application.model.dto.ReportDTO;
import com.github.kai9026.myfinancialapi.application.service.ExpensesReportHTMLService;
import com.github.kai9026.myfinancialapi.application.service.ExpensesReportSheetService;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/expenses/report")
public class ReportResource {

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance expenses(ReportDTO report);
  }

  private final ExpensesReportSheetService reportService;

  private final ExpensesReportHTMLService reportHTMLService;

  public ReportResource(final ExpensesReportSheetService reportService,
      final ExpensesReportHTMLService reportHTMLService) {

    this.reportService = reportService;
    this.reportHTMLService = reportHTMLService;
  }

  @GET
  @Path("web")
  @Produces(MediaType.TEXT_HTML)
  @Blocking
  public String generateCurrentHtmlReport(@QueryParam("month") Integer month) {
    return Templates.expenses(reportHTMLService.generateReportByMonth(month)).render();
  }

  @GET
  @Path("/sheet")
  @Produces("application/vnd.ms-excel")
  @Blocking
  public Response generateYearInProgressSheetReport() {
    final var reportInProgress = reportService.generateReportInProgress();

    return Response.ok(reportInProgress)
      .header("Content-Disposition", "attachment;filename=expenses.xlsx")
      .build();
  }
}
