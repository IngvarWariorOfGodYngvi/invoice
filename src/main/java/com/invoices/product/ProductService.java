package com.invoices.product;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Dodać edytowanie produktów, dodać zmianę cen i tworzenie autentycznie dziennego raportu bez możliwości
    // zmiany ceny po utworzeniu rapotu tak by nie można było ingerować w raport inaczej jak celową zmianą

    public ResponseEntity<?> getAllProducts() {

        List<Product> list = productRepository.findAll();
        list.sort(Comparator.comparing(Product::getName));
        return ResponseEntity.ok(list);

    }

    public ResponseEntity<?> addNewProduct(String name, BigDecimal price) {
        if (name.isEmpty() || price.equals(BigDecimal.ZERO)) {
            return ResponseEntity.badRequest().body("Nie można dodać produktu");
        }
        if (productRepository.existsByName(name.trim())) {
            return ResponseEntity.badRequest().body("Taki produkt już istnieje w bazie");
        }
        BigDecimal round = price.setScale(2, RoundingMode.HALF_EVEN);
        productRepository.save(Product.builder()
                .name(name)
                .price(round)
                .build());

        return ResponseEntity.ok("Zapisano nowy produkt");
    }

    public ResponseEntity<?> editProductPrice(String uuid, BigDecimal newPrice) {
        Product product = productRepository.getById(uuid);
        product.setPrice(newPrice);
        productRepository.save(product);
        return ResponseEntity.ok("Zmieniono cenę " + product.getName());
    }
}
