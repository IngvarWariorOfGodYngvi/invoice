package com.invoices.dailyreport;

import jdk.jfr.Relational;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dailyReport")
@CrossOrigin
public class DailyReportController {

    private final DailyReportService dailyReportService;

    public DailyReportController(DailyReportService dailyReportService) {
        this.dailyReportService = dailyReportService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam @Nullable String month,@RequestParam @Nullable String year){
        return dailyReportService.getAllDailyReports(month,year);
    }

    @PostMapping("/addDailyReport")
    public ResponseEntity<?> addDailyReport(@RequestBody List<DailyReportProduct> reportList, @RequestParam String date){

        return dailyReportService.addDailyReport(reportList,LocalDate.parse(date));
    }
}
