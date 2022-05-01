package com.invoices.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "org.hibernate.id.UUIDGenerator")
    private String uuid;

    private String sellerUUID;
    private String sellerName;

    private String number;
    private String description;
    private String categoryName;

    private BigDecimal amount;

    private LocalDate dateOfPayment;
    private LocalDate dateOfExposed;

    private boolean transfer_cash; /* transfer = false | cash = true */
    private boolean bill_invoice; /* bill = false | invoice = true */

    public String getUuid() {
        return uuid;
    }

    public String getSellerUUID() {
        return sellerUUID;
    }

    public void setSellerUUID(String sellerUUID) {
        this.sellerUUID = sellerUUID;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public LocalDate getDateOfExposed() {
        return dateOfExposed;
    }

    public void setDateOfExposed(LocalDate dateOfExposed) {
        this.dateOfExposed = dateOfExposed;
    }

    public boolean isTransfer_cash() {
        return transfer_cash;
    }

    public void setTransfer_cash(boolean transfer_cash) {
        this.transfer_cash = transfer_cash;
    }

    public boolean isBill_invoice() {
        return bill_invoice;
    }

    public void setBill_invoice(boolean bill_invoice) {
        this.bill_invoice = bill_invoice;
    }
}
