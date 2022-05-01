package com.invoices.category;

import com.invoices.invoice.Invoice;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

    Category save(Category entity);

    Category findByName(String name);

    boolean existsByName(String name);

}
