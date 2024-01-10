package com.api.kiranastore.controllers;

import com.api.kiranastore.models.report.ReportDates;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.transReport.ReportServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private final ReportServiceImpl reportService;

    public OwnerController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> Report(@RequestBody ReportDates reportDates){
        ApiResponse apiResponse =  reportService.generateReport(reportDates);
        //System.out.println("responseEntity");
        return ResponseEntity.ok(apiResponse);
    }
}
