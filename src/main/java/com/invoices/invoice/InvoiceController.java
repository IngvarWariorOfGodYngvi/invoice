package com.invoices.invoice;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
@CrossOrigin
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam boolean transfer_cash, @RequestParam @Nullable String year, @RequestParam @Nullable String month) {
        return invoiceService.getAllInvoices(transfer_cash, year, month);
    }

    @GetMapping("/getAllIncome")
    public ResponseEntity<?> getAllIncome(@RequestParam @Nullable String year, @RequestParam @Nullable String month) {
        return invoiceService.getAllIncome(year, month);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceDto invoice, @RequestParam String sellerName, @RequestParam String sellerNIP) {
        return invoiceService.createInvoice(invoice, sellerName, sellerNIP);
    }
}
