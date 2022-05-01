package com.invoices.invoice;

import com.invoices.Mapping.InvoiceMapping;
import com.invoices.category.CategoryRepository;
import com.invoices.category.CategoryService;
import com.invoices.seller.Seller;
import com.invoices.seller.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoicesRepository invoicesRepository;
    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;
    private final SellerService sellerService;

    public InvoiceService(InvoicesRepository invoicesRepository, CategoryRepository categoryRepository, CategoryService categoryService, SellerService sellerService) {
        this.invoicesRepository = invoicesRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.sellerService = sellerService;
    }

    public ResponseEntity<?> getAllInvoices(boolean transfer_cash, String year, String month) {
        if (month == null || month.equals("null")) {
            month = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pl"));
        }
        if (year == null || year.equals("null")) {
            year = String.valueOf(LocalDate.now().getYear());
        }
        String finalMonth = month;
        String finalYear = year;
        List<Invoice> all = invoicesRepository.findAll()
                .stream()
                .filter(f->Double.parseDouble(f.getAmount().toString())>0)
                .filter(f -> f.getDateOfPayment().getYear() == Integer.parseInt(finalYear))
                .filter(f -> f.getDateOfPayment().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pl")).equals(finalMonth))
                .filter(f -> f.isTransfer_cash() == transfer_cash)
                .sorted(Comparator.comparing(Invoice::getDateOfPayment).thenComparing(Invoice::getDateOfExposed).reversed())
                .collect(Collectors.toList());

        return ResponseEntity.ok(all);

    }

    public ResponseEntity<?> createInvoice(InvoiceDto invoice, String sellerName, String sellerNIP) {

        if (invoice.getAmount() == null || invoice.getAmount().equals("0") || invoice.getAmount().equals("null") || invoice.getAmount().equals("")) {
            return ResponseEntity.badRequest().body("Faktura musi mieć podaną kwotę");
        }
        if (invoice.getDateOfPayment() == null) {
            return ResponseEntity.badRequest().body("Wskaż datę opłacenia faktury");
        }
        if (!categoryRepository.existsByName(invoice.getCategoryName())) {
            categoryService.addNewCategory(invoice.getCategoryName());
        }
        Seller seller = new Seller();
        if (sellerName != null && !sellerName.equals("") && !sellerName.equals("null")) {
            seller = sellerService.getSellerByName(sellerName);
        }
        if (sellerNIP != null && !sellerNIP.equals("") && !sellerNIP.equals("null")) {
            seller = sellerService.getSellerByNip(sellerNIP);
        }
        if (seller.getInvoicesList().stream().anyMatch(a -> a.getNumber().equals(invoice.getNumber()))) {
            return ResponseEntity.badRequest().body("Taka faktura/paragon już się znajduje u Tego sprzedawcy");
        }
        Invoice map = InvoiceMapping.map(invoice);
        map.setSellerName(seller.getName());
        map.setSellerUUID(seller.getUuid());
        Invoice save = invoicesRepository.save(map);
        String invoice2 = "fakturę";
        if (!invoice.isBill_invoice()) {
            invoice2 = "paragon";
        }
        sellerService.addInvoiceToSeller(seller.getUuid(), save);
        return ResponseEntity.ok("Zapisano " + invoice2);
    }


    public ResponseEntity<?> getAllIncome(String year, String month) {
        if (month == null || month.equals("null")) {
            month = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pl"));
        }
        if (year == null || year.equals("null")) {
            year = String.valueOf(LocalDate.now().getYear());
        }
        String finalMonth = month;
        String finalYear = year;
        List<Invoice> all = invoicesRepository.findAll()
                .stream()
                .filter(f->Double.parseDouble(f.getAmount().toString())<0)
                .filter(f -> f.getDateOfPayment().getYear() == Integer.parseInt(finalYear))
                .filter(f -> f.getDateOfPayment().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pl")).equals(finalMonth))
                .sorted(Comparator.comparing(Invoice::getDateOfPayment).thenComparing(Invoice::getDateOfExposed).reversed())
                .collect(Collectors.toList());
        all.forEach(e->{
            String replace = String.valueOf(e.getAmount()).replace("-", "");
            e.setAmount(BigDecimal.valueOf(Double.parseDouble(replace)));
        });

        return ResponseEntity.ok(all);
    }
}
