package com.api.kiranastore.services.transReport;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.models.report.ReportDates;
import com.api.kiranastore.models.report.ReportResponse;
import com.api.kiranastore.repo.TransactionsRepo;
import com.api.kiranastore.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{

    private final TransactionsRepo transactionsRepo;

    public ReportServiceImpl(TransactionsRepo transactionsRepo) {
        this.transactionsRepo = transactionsRepo;
    }

    @Override
    public ApiResponse generateReport(ReportDates reportDates) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate fromDate = LocalDate.parse(reportDates.getFromTime());
        LocalDate toDate = LocalDate.parse(reportDates.getToTime());
        LocalDateTime fromDateTime = LocalDateTime.of(fromDate, midnight);
        LocalDateTime toDateTime = LocalDateTime.of(toDate, midnight);
        List<Transactions> transReport =  transactionsRepo.findTransactionBetweenDates(fromDateTime,toDateTime);
        ReportResponse reportResponse = new ReportResponse(true,transReport,200,"Trans repo", HttpStatus.OK);
        return reportResponse.getApiResponse();
    }
}
