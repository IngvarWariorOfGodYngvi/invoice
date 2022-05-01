package com.invoices.Mapping;

import com.invoices.invoice.Invoice;
import com.invoices.invoice.InvoiceDto;

public class InvoiceMapping {

    public static InvoiceDto map(Invoice e) {
        return InvoiceDto.builder()
                .uuid(e.getUuid())
                .number(e.getNumber())
                .dateOfExposed(e.getDateOfExposed())
                .dateOfPayment(e.getDateOfPayment())
                .amount(e.getAmount())
                .categoryName(e.getCategoryName())
                .description(e.getDescription())
                .sellerName(e.getSellerName())
                .sellerUUID(e.getSellerUUID())
                .bill_invoice(e.isBill_invoice())
                .transfer_cash(e.isTransfer_cash())

                .build();
    }

    public static Invoice map(InvoiceDto e) {
        return Invoice.builder()
                .number(e.getNumber())
                .dateOfExposed(e.getDateOfExposed())
                .dateOfPayment(e.getDateOfPayment())
                .amount(e.getAmount())
                .categoryName(e.getCategoryName())
                .description(e.getDescription())
                .sellerName(e.getSellerName())
                .sellerUUID(e.getSellerUUID())
                .bill_invoice(e.isBill_invoice())
                .transfer_cash(e.isTransfer_cash())
                .build();
    }

}
