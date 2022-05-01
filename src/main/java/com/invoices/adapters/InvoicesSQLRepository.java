package com.invoices.adapters;

import com.invoices.invoice.Invoice;
import com.invoices.invoice.InvoicesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface InvoicesSQLRepository extends InvoicesRepository, JpaRepository<Invoice,String> {
}
