package com.invoices.report;

import com.invoices.dailyreport.DailyReport;
import com.invoices.dailyreport.DailyReportRepository;
import com.invoices.invoice.Invoice;
import com.invoices.invoice.InvoicesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final InvoicesRepository invoicesRepository;
    private final DailyReportRepository dailyReportRepository;

    public ReportService(InvoicesRepository invoicesRepository, DailyReportRepository dailyReportRepository) {
        this.invoicesRepository = invoicesRepository;
        this.dailyReportRepository = dailyReportRepository;
    }

    public List<String> getAllYearsForReports() {
        List<String> list = new ArrayList<>();
        invoicesRepository.findAll().forEach(e -> list.add(String.valueOf(e.getDateOfPayment().getYear())));
        dailyReportRepository.findAll().forEach((e -> list.add(String.valueOf(e.getDate().getYear()))));
        return list.stream().distinct().sorted().collect(Collectors.toList());
    }

    public List<String> getAllMonthsForReports() {
        List<Month> list1 = new ArrayList<>();
        List<String> list = new ArrayList<>();
        invoicesRepository.findAll().forEach(e -> list1.add(e.getDateOfPayment().getMonth()));
        dailyReportRepository.findAll().forEach((e -> list1.add(e.getDate().getMonth())));

        list1.stream().distinct().sorted().forEach(e -> list.add(monthConverter(e)));

        return list;
    }

    private String monthConverter(Month month) {
        return month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pl"));
    }

    public ResponseEntity<?> getReportWithParams(String reportType, String year, String month) {
        Report report = new Report();
        List<Invoice> invoiceList;
        List<DailyReport> dailyReportList;
//        <th class="text-left">Rozchody z faktur - Klub</th>
//              <th class="text-left">Rozchody z faktur - Biuro</th>
//              <th class="text-left">Suma Rozchodów</th>
//              <th class="text-left">Przychody z Kasy</th>
//              <th class="text-left">Przychody z Biura</th>
//              <th class="text-left">Suma Przychodów</th>
        if (reportType.equals("Raport Miesięczny")) {
            invoiceList = invoicesRepository.findAll()
                    .stream()
                    .filter(f -> f.getDateOfPayment().getYear() == Integer.parseInt(year))
                    .filter(f -> f.getDateOfPayment().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pl")).equals(month))
                    .collect(Collectors.toList());
            //faktury opłacone przez klub
            BigDecimal outgoingFromInvoices_Club = new BigDecimal(0);
            List<BigDecimal> outgoingFromInvoices_ClubList = new ArrayList<>();
            invoiceList.stream()
                    .filter(f -> Double.parseDouble(String.valueOf(f.getAmount())) > 0)
                    .filter(Invoice::isTransfer_cash)
                    .forEach(e -> outgoingFromInvoices_ClubList.add(e.getAmount()));
            for (BigDecimal val : outgoingFromInvoices_ClubList) {
                outgoingFromInvoices_Club = outgoingFromInvoices_Club.add(val);
            }

            report.setOutgoingsFromInvoices_Club(outgoingFromInvoices_Club);
            //faktury opłacone przez biuro
            BigDecimal outgoingFromInvoices_Office = new BigDecimal(0);
            List<BigDecimal> outgoingFromInvoices_OfficeList = new ArrayList<>();
            invoiceList.stream()
                    .filter(f -> Double.parseDouble(String.valueOf(f.getAmount())) > 0)
                    .filter(f -> !f.isTransfer_cash())
                    .forEach(e -> outgoingFromInvoices_OfficeList.add(e.getAmount()));
            for (BigDecimal val : outgoingFromInvoices_OfficeList) {
                outgoingFromInvoices_Office = outgoingFromInvoices_Office.add(val);
            }
            report.setOutgoingsFromInvoices_Office(outgoingFromInvoices_Office);
            //suma wydatków

            report.setTotalExpenses(outgoingFromInvoices_Club.add(outgoingFromInvoices_Office));

            dailyReportList = dailyReportRepository.findAll()
                    .stream()
                    .filter(f -> f.getDate().getYear() == Integer.parseInt(year))
                    .filter(f -> f.getDate().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pl")).equals(month))
                    .collect(Collectors.toList());
            //przychód z raportów dziennych

            BigDecimal revenuesFromCashDesk = new BigDecimal(0);
            List<BigDecimal> revenuesFromCashDeskList = new ArrayList<>();

            dailyReportList.forEach(e -> revenuesFromCashDeskList.add(e.getSum()));

            for (BigDecimal val : revenuesFromCashDeskList) {
                revenuesFromCashDesk = revenuesFromCashDesk.add(val);
            }
            report.setRevenuesFromCashDesk(revenuesFromCashDesk);
            //przychód z biura i KP

            BigDecimal revenuesFromOffice = new BigDecimal(0);
            List<BigDecimal> revenuesFromOfficeList = new ArrayList<>();

            invoiceList.stream()
                    .filter(f -> Double.parseDouble(String.valueOf(f.getAmount())) < 0)
                    .forEach(e -> revenuesFromOfficeList.add(e.getAmount()));

            for (BigDecimal val: revenuesFromOfficeList) {
                revenuesFromOffice = revenuesFromOffice.add(val);
            }
            String replace = revenuesFromOffice.toString().replace("-", "");

            revenuesFromOffice = BigDecimal.valueOf(Double.parseDouble(replace));


            report.setRevenueFromOffice(revenuesFromOffice);
            //suma przychodów
            report.setTotalIncome(revenuesFromCashDesk.add(revenuesFromOffice));


//            List<BigDecimal> values = new ArrayList<>();
//            List<BigDecimal> values1 = new ArrayList<>();
//            invoiceList.stream()
//                    .filter(Invoice::isTransfer_cash)
//                    .forEach(e -> values.add(BigDecimal.valueOf(Double.parseDouble(e.getAmount()))));
//            BigDecimal result = new BigDecimal(0);
//            BigDecimal result1 = new BigDecimal(0);
//            for (BigDecimal bigDecimal : values) result = result.add(bigDecimal);
//            report.setOutgoingsFromInvoices_Club(result);
//            invoiceList.stream()
//                    .filter(f -> !f.isTransfer_cash())
//                    .forEach(e -> values1.add(BigDecimal.valueOf(Double.parseDouble(e.getAmount()))));
//            for (BigDecimal bigDecimal : values1) result1 = result1.add(bigDecimal);
//            {
//                report.setOutgoingsFromInvoices_Office(result1);
//            }
//            report.setTotalExpenses(result.add(result1));
        }
        return ResponseEntity.ok(report);
    }
}
