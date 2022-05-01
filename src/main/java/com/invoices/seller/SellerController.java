package com.invoices.seller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
@CrossOrigin
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return sellerService.getAllSellers();
    }

    @GetMapping("/getAllSellersListNames")
    public List<String> getAllSellersListNames() {
        return sellerService.getAllSellersListNames();
    }

    @GetMapping("/getAllSellersListNip")
    public List<String> getAllSellersListNip() {
        return sellerService.getAllSellersListNip();
    }

    @GetMapping("/getSellerById")
    public ResponseEntity<?> getSellerById(@RequestParam String sellerUUID) {
        return sellerService.getSellerById(sellerUUID);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewSeller(@RequestBody SellerDto seller) {
        return sellerService.createNewSeller(seller);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSellerProp(@RequestParam String sellerUUID, @RequestBody SellerDto seller) {
        return sellerService.updateSellerProp(sellerUUID, seller);
    }
}
