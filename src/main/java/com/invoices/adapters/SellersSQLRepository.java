package com.invoices.adapters;

import com.invoices.seller.Seller;
import com.invoices.seller.SellersRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface SellersSQLRepository extends SellersRepository, JpaRepository<Seller, String> {



}
