package com.invoices.invoice;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class InvoiceDto implements Serializable {
    private final String uuid;
    private final String sellerUUID;
    private final String sellerName;
    private final String number;
    private final String description;
    private final String categoryName;
    private final BigDecimal amount;
    private final LocalDate dateOfPayment;
    private final LocalDate dateOfExposed;
    private final boolean transfer_cash;
    private final boolean bill_invoice;
}
