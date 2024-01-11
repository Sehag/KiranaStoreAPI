package com.api.kiranastore.services.transReport;

import com.api.kiranastore.models.report.ReportDates;
import com.api.kiranastore.response.ApiResponse;

public interface ReportService {

    public ApiResponse generateReport(ReportDates reportDates);

    public ApiResponse generateWeeklyReport();

    public ApiResponse generateMonthlyReport();

    public ApiResponse generateYearlyReport();
}
