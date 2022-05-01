package com.invoices.seller;

import java.util.List;

public interface SellersRepository {

    List<Seller> findAll();

    Seller save(Seller entity);

    void delete(Seller entity);

    void deleteById(String uuid);

    boolean existsByName(String name);

    boolean existsByNIP(String nip);

    boolean existsById(String uuid);

    Seller getById(String uuid);

    Seller getByName(String sellerName);

    Seller getByNIP(String sellerNip);
}
