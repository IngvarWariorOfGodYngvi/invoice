package com.invoices.product;

import com.invoices.seller.SellerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/addNew")
    public ResponseEntity<?> addNewProduct(@RequestParam String name, @RequestParam String price) {
        return productService.addNewProduct(name, BigDecimal.valueOf(Double.parseDouble(price)));
    }

    @PutMapping("/editPrice")
    public ResponseEntity<?> editPrice(@RequestParam String uuid, @RequestParam String newPrice) {
        return productService.editProductPrice(uuid, BigDecimal.valueOf(Double.parseDouble(newPrice)));
    }
}
