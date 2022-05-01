package com.invoices.dailyreport;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class DailyReportService {

    private final DailyReportProductRepository dailyReportProductRepository;
    private final DailyReportRepository dailyReportRepository;

    public DailyReportService(DailyReportProductRepository dailyReportProductRepository, DailyReportRepository dailyReportRepository) {
        this.dailyReportProductRepository = dailyReportProductRepository;
        this.dailyReportRepository = dailyReportRepository;
    }

    public ResponseEntity<?> addDailyReport(List<DailyReportProduct> reportList, LocalDate date) {
        if (reportList.isEmpty()) {
            return ResponseEntity.badRequest().body("Coś jest nie tak jak być powinno");
        } else {
            // utworzyć listę do encji
            List<DailyReportProduct> list = new ArrayList<>();
            BigDecimal sum = BigDecimal.ZERO;

            for (DailyReportProduct prod : reportList) {
                if (prod.getQuantity()!=null&&prod.getQuantity() > 0) {
                    // zapisać produkt
                    DailyReportProduct save = dailyReportProductRepository.save(DailyReportProduct.builder()
                            .name(prod.getName())
                            .quantity(prod.getQuantity())
                            .price(prod.getPrice())
                            .sum(prod.getPrice().multiply(BigDecimal.valueOf(prod.getQuantity())))
                            .build());
                    //dodać go do listy
                    list.add(save);
                    sum = sum.add(BigDecimal.valueOf(prod.getQuantity()).multiply(prod.getPrice()));
                }
            }
            //zapisać encję raportu
            dailyReportRepository.save(DailyReport.builder()
                    .date(date)
                    .productList(list)
                    .sum(sum)
                    .build());
        }
        return ResponseEntity.ok("Utworzono dzienny raport");
    }

    public ResponseEntity<?> getAllDailyReports(String month, String year) {
        if (month == null || month.equals("null")) {
            month = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pl"));
        }
        if (year == null || year.equals("null")) {
            year = String.valueOf(LocalDate.now().getYear());
        }
        String finalMonth = month;
        String finalYear = year;
        List<DailyReport> collect = dailyReportRepository.findAll()
                .stream()
                .filter(f->f.getDate().getYear()==Integer.parseInt(finalYear))
                .filter(f -> f.getDate().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pl")).equals(finalMonth))
                .collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }
}
