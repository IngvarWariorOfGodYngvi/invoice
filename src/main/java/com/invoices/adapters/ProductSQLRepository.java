package com.invoices.adapters;

import com.invoices.product.Product;
import com.invoices.product.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProductSQLRepository extends ProductRepository, JpaRepository<Product,String> {
}
