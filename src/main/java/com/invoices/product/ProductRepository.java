package com.invoices.product;

import com.invoices.invoice.Invoice;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product entity);

    void delete(Product entity);

    void deleteById(String uuid);

    Product getById(String uuid);

    boolean existsByName(String name);
}
