package com.invoices.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    private BigDecimal outgoingsFromInvoices_Club;
    private BigDecimal outgoingsFromInvoices_Office;
    private BigDecimal totalExpenses;
    private BigDecimal revenuesFromCashDesk;
    private BigDecimal RevenueFromOffice;
    private BigDecimal totalIncome;

}
