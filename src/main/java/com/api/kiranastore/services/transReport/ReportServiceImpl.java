package com.api.kiranastore.services.transReport;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.enums.TransType;
import com.api.kiranastore.models.report.ReportDates;
import com.api.kiranastore.models.report.ReportDetails;
import com.api.kiranastore.models.report.ReportResponse;
import com.api.kiranastore.repo.TransactionsRepo;
import com.api.kiranastore.response.ApiResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final TransactionsRepo transactionsRepo;

    public ReportServiceImpl(TransactionsRepo transactionsRepo) {
        this.transactionsRepo = transactionsRepo;
    }

    /**
     * Generate financial report based on two dates provided by the owner
     *
     * @param reportDates From date and to date
     * @return Financial report between two dates
     */
    @Override
    public ApiResponse generateReport(ReportDates reportDates) {
        LocalDate fromDate = LocalDate.parse(reportDates.getFromTime());
        LocalTime fromTime = LocalTime.MIDNIGHT;
        LocalDate toDate = LocalDate.parse(reportDates.getToTime());
        LocalTime toTime = LocalTime.MIDNIGHT.minusSeconds(1);
        LocalDateTime fromDateTime = LocalDateTime.of(fromDate, fromTime);
        LocalDateTime toDateTime = LocalDateTime.of(toDate, toTime);

        List<Transactions> totalTrans =
                transactionsRepo.findTransactionBetweenDates(fromDateTime, toDateTime);
        ReportResponse reportResponse = calculateReport(totalTrans);
        return reportResponse.getApiResponse();
    }

    /**
     * Generate financial report based on the last 7 days
     *
     * @return Financial report of the last 7 days
     */
    @Override
    public ApiResponse generateWeeklyReport() {
        LocalDateTime toDateTime = LocalDateTime.now();
        LocalTime fromTime = LocalTime.MIDNIGHT;
        LocalDate fromDate = LocalDate.now().minusDays(7);
        LocalDateTime fromDateTime = LocalDateTime.of(fromDate, fromTime);
        List<Transactions> totalTrans =
                transactionsRepo.findTransactionBetweenDates(fromDateTime, toDateTime);
        ReportResponse reportResponse = calculateReport(totalTrans);
        return reportResponse.getApiResponse();
    }

    /**
     * Generate financial report of the current month
     *
     * @return Financial report of the current month
     */
    @Override
    public ApiResponse generateMonthlyReport() {
        LocalDateTime toDateTime = LocalDateTime.now();
        LocalTime fromTime = LocalTime.MIDNIGHT;
        LocalDate fromDate = LocalDate.now().withDayOfMonth(1);
        LocalDateTime fromDateTime = LocalDateTime.of(fromDate, fromTime);
        List<Transactions> totalTrans =
                transactionsRepo.findTransactionBetweenDates(fromDateTime, toDateTime);
        ReportResponse reportResponse = calculateReport(totalTrans);
        return reportResponse.getApiResponse();
    }

    /**
     * Generate financial report based of the current year
     *
     * @return Financial report of the current year
     */
    @Override
    public ApiResponse generateYearlyReport() {
        LocalDateTime toDateTime = LocalDateTime.now();
        LocalTime fromTime = LocalTime.MIDNIGHT;
        LocalDate fromDate = LocalDate.now().withDayOfYear(1);
        LocalDateTime fromDateTime = LocalDateTime.of(fromDate, fromTime);
        List<Transactions> totalTrans =
                transactionsRepo.findTransactionBetweenDates(fromDateTime, toDateTime);
        ReportResponse reportResponse = calculateReport(totalTrans);
        return reportResponse.getApiResponse();
    }

    /**
     * Calculates the report
     *
     * @param totalTrans List of transactions
     * @return
     */
    private ReportResponse calculateReport(List<Transactions> totalTrans) {
        double creditAmount = 0.0;
        double debitAmount = 0.0;
        int numOfTrans = totalTrans.size();

        for (Transactions trans : totalTrans) {
            if (trans.getTransType().equals(TransType.CREDIT)) {
                creditAmount += trans.getAmount();
            } else {
                debitAmount += trans.getAmount();
            }
        }

        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setCreditAmount(creditAmount);
        reportDetails.setDebitAmount(debitAmount);
        reportDetails.setNumOfTrans(numOfTrans);
        reportDetails.setNetFlow(creditAmount - debitAmount);
        return new ReportResponse(true, reportDetails, 200, "Trans report", HttpStatus.OK);
    }
}
