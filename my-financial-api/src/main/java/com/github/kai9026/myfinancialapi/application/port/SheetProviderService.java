package com.github.kai9026.myfinancialapi.application.port;

import com.github.kai9026.myfinancialapi.domain.expense.Expense;
import java.io.File;
import java.time.Month;
import java.util.List;
import java.util.TreeMap;

public interface SheetProviderService {

  File generateSheetReport(TreeMap<Month, List<Expense>> expenses);
}
