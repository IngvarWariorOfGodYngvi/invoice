package com.invoices.report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/getYears")
    public List<String> getAllYearsFromInvoices() {
        return reportService.getAllYearsForReports();
    }

    @GetMapping("/getMonths")
    public List<String> getAllMonthsFromInvoices() {
        return reportService.getAllMonthsForReports();
    }

    @GetMapping("/getReport")
    public ResponseEntity<?> getReportWithParams(@RequestParam String reportType, @RequestParam String year, @RequestParam String month) {
        return reportService.getReportWithParams(reportType, year, month);
    }
}
