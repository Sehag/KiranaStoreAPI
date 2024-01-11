package com.api.kiranastore.models.report;

import lombok.Data;

@Data
public class ReportDetails {
    private int numOfTrans;
    private double creditAmount;
    private double debitAmount;
    private double netFlow;
}
