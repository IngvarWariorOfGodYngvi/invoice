package com.invoices.Mapping;

import com.invoices.seller.Seller;
import com.invoices.seller.SellerDto;

public class SellerMapping {

    public static Seller map(SellerDto s){
        return Seller.builder()
                .name(s.getName())
                .NIP(s.getNIP())
                .invoicesList(s.getInvoicesList())
                .build();
    }
    public static SellerDto map(Seller s){
        return SellerDto.builder()
                .uuid(s.getUuid())
                .name(s.getName())
                .NIP(s.getNIP())
                .invoicesList(s.getInvoicesList())
                .build();
    }
}
