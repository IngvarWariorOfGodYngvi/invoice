package com.invoices.seller;

import com.invoices.Mapping.SellerMapping;
import com.invoices.invoice.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    private final SellersRepository sellersRepository;

    private final Logger log = LogManager.getLogger(getClass());

    public SellerService(SellersRepository sellersRepository) {
        this.sellersRepository = sellersRepository;
    }


    public ResponseEntity<?> createNewSeller(SellerDto dto) {

        ResponseEntity<String> badRequestBody = ResponseEntity.badRequest().body("\"Taki sprzedawca już istnieje w bazie\"");

        dto.setName(dto.getName().trim());
        dto.setNIP(dto.getNIP().replaceAll(" ","").trim());
        if (sellersRepository.existsByNIP(dto.getNIP())) {
            return badRequestBody;
        }
        Seller save = sellersRepository.save(SellerMapping.map(dto));
        log.info("Utworzono nowego sprzedawcę : " + save.toString());
        return ResponseEntity.ok("\"Utworzono nowego sprzedawcę\"");
    }

    public ResponseEntity<?> updateSellerProp(String uuid, SellerDto dto) {

        if (sellersRepository.existsById(uuid)) {
            Seller entity = sellersRepository.getById(uuid);

            String body = "";
            dto.setName(dto.getName().trim());
            dto.setNIP(dto.getNIP().replaceAll(" ","").trim());
            if (!entity.getName().equals(dto.getName()) && !dto.getName().isBlank()) {
                log.info("zmieniono nazwę z : " + entity.getName() + " na : " + dto.getName());
                entity.setName(dto.getName());
                body = body.concat(dto.getName());
            }
            if (!entity.getNIP().equals(dto.getNIP()) && !dto.getNIP().isBlank()) {
                if (sellersRepository.existsByNIP(dto.getNIP())) {
                    log.info("Taki numer już się znajduje w bazie");
                    return ResponseEntity.badRequest().body("\"Taki numer już się znajduje w bazie\"");
                }
                log.info("zmieniono NIP z : " + entity.getNIP() + " na : " + dto.getNIP());
                entity.setNIP(dto.getNIP());
                body = body.concat(dto.getNIP());
            }

            sellersRepository.save(entity);

            return ResponseEntity.ok("\"zmieniono " + body + "\"");
        } else {
            ResponseEntity.badRequest().body("\"Nie znaleziono sprzedawcy\"");
        }

        return null;
    }

    public void addInvoiceToSeller(String uuid, Invoice save){
        Seller seller = sellersRepository.getById(uuid);

        List<Invoice> invoicesList = seller.getInvoicesList();

        invoicesList.add(save);
        seller.setInvoicesList(invoicesList);
        sellersRepository.save(seller);

    }

    public ResponseEntity<?> getAllSellers() {

        List<Seller> entities = sellersRepository.findAll();
        List<SellerDto> dtos = new ArrayList<>();

        if (entities.isEmpty()) {
            log.info("Nie ma nic do wyświetlenia");
            return ResponseEntity.ok("\"Nie ma nic do wyświetlenia\"");
        } else {
            entities.forEach(e -> {
                dtos.add(SellerMapping.map(e));
            });
            log.info("wyświetlam listę sprzedawców");
            return ResponseEntity.ok(dtos);
        }
    }

    public List<String> getAllSellersListNames() {
        List<String> list = new ArrayList<>();
        List<Seller> entities = sellersRepository.findAll();

        if (entities.isEmpty()) {
            log.info("Nie ma nic do wyświetlenia do szukajki po nazwach");
        } else {
            entities.forEach(e -> {
                list.add(e.getName());
            });
            log.info("wyświetlam listę sprzedawców do szukajki po nazwach");
        }
        return list;
    }

    public List<String> getAllSellersListNip() {
        List<String> list = new ArrayList<>();
        List<Seller> entities = sellersRepository.findAll();

        if (entities.isEmpty()) {
            log.info("Nie ma nic do wyświetlenia do szukajki po Nipie");
        } else {
            entities.forEach(e -> {
                if (e.getNIP() != null && !e.getNIP().isEmpty())
                    list.add(e.getNIP());
            });
            log.info("wyświetlam listę sprzedawców do szukajki po Nipie");
        }
        return list;
    }

    public ResponseEntity<?> getSellerById(String uuid) {

        if (!sellersRepository.existsById(uuid)) {
            return ResponseEntity.badRequest().body("\"Nie znaleziono sprzedawcy\"");
        } else {
            return ResponseEntity.ok(sellersRepository.getById(uuid));
        }
    }

    public Seller getSellerByName(String sellerName) {
        if (!sellersRepository.existsByName(sellerName)) {
            return null;
        } else {
            return sellersRepository.getByName(sellerName);
        }
    }

    public Seller getSellerByNip(String sellerNip) {
        if (!sellersRepository.existsByNIP(sellerNip)) {
            return null;
        } else {
            return sellersRepository.getByNIP(sellerNip);
        }
    }
}
