package com.invoices.buyer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyersRepository extends JpaRepository<Buyer, String> {

}
