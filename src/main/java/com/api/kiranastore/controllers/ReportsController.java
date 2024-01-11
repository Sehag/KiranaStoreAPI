package com.api.kiranastore.controllers;

import com.api.kiranastore.models.report.ReportDates;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.transReport.ReportServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportsController {

    private final ReportServiceImpl reportService;

    public ReportsController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    /**
     * Access financial report for a period of time
     *
     * @param reportDates consists of from-date and to-date
     */
    @PostMapping("/customDates")
    public ResponseEntity<ApiResponse> Report(@RequestBody ReportDates reportDates) {
        ApiResponse apiResponse = reportService.generateReport(reportDates);
        return ResponseEntity.ok(apiResponse);
    }

    /** Access financial report for the last 7 days */
    @GetMapping("/weekly")
    public ResponseEntity<ApiResponse> WeeklyReport() {
        ApiResponse apiResponse = reportService.generateWeeklyReport();
        return ResponseEntity.ok(apiResponse);
    }

    /** Access financial report for the current month */
    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse> MonthlyReport() {
        ApiResponse apiResponse = reportService.generateMonthlyReport();
        return ResponseEntity.ok(apiResponse);
    }

    /** Access financial report for the current year */
    @GetMapping("/yearly")
    public ResponseEntity<ApiResponse> YearlyReport() {
        ApiResponse apiResponse = reportService.generateYearlyReport();
        return ResponseEntity.ok(apiResponse);
    }
}
