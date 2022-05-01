package com.invoices.invoice;

import java.util.List;

public interface InvoicesRepository {

    List<Invoice> findAll();

    Invoice save(Invoice entity);

    void delete(Invoice entity);

    void deleteById(String uuid);

    boolean existsById(String uuid);

    Invoice getById(String uuid);

    boolean existsByNumber(String number);

}
