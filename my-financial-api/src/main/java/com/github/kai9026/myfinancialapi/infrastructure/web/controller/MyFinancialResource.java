package com.github.kai9026.myfinancialapi.infrastructure.web.controller;

import com.github.kai9026.myfinancialapi.application.model.dto.ExpenseDTO;
import com.github.kai9026.myfinancialapi.application.usecase.ExpenseUseCase;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path(MyFinancialResource.EXPENSES_URI)
public class MyFinancialResource {

  static final String EXPENSES_URI = "/expenses";

  private final ExpenseUseCase expenseUseCase;

  public MyFinancialResource(final ExpenseUseCase expenseUseCase) {
    this.expenseUseCase = expenseUseCase;
  }

  @GET
  @Path("public")
  @PermitAll
  public Uni<String> publicPath() {

    return Uni.createFrom().item("Public endpoint");
  }

  @GET
  @Path("{id}")
  @PermitAll
  @Produces(APPLICATION_JSON)
  public Uni<ExpenseDTO> getExpensesById(@PathParam("id") Long expenseId) {

    return this.expenseUseCase.getExpenseById(expenseId);
  }

  @POST
  @PermitAll
  @Produces(APPLICATION_JSON)
  @Blocking
  public Uni<Response> createExpense(ExpenseDTO newExpense) {

    return expenseUseCase.createExpense(newExpense)
      .onItem().transform(id -> URI.create(EXPENSES_URI + "/" + id))
      .onItem().transform(uri -> Response.created(uri).build());
  }

  @GET
  @Produces(APPLICATION_JSON)
  public Multi<ExpenseDTO> getExpensesFromMonth(@QueryParam("month") Integer currentMonth) {

    return expenseUseCase.getExpensesFromCurrentMonth(currentMonth);
  }
}