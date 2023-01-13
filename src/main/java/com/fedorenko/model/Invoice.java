package com.fedorenko.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Invoice {
    private final List<Technics> LIST_Of_TECHNICS = new ArrayList<>();;
    private final Customer customer;
    private InvoiceType invoiceType;
    private static final int LIMIT_TYPE_PRICE = 3000;

    public Invoice(@NonNull final Customer customer) {
        this.customer = customer;
        invoiceType = InvoiceType.RETAIL;
    }

    public void addProduct(@NonNull final Technics technics) {
        LIST_Of_TECHNICS.add(technics);
        isWholesale();
    }

    public List<Technics> getAll() {
        return LIST_Of_TECHNICS;
    }

    @Override
    public String toString() {
        return "Invoice{" + " TIME[" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "] USER_DATA[customer=" + customer +
                "] INVOICE_DATA" + getAll() +
                '}';
    }

    private void isWholesale() {
        int priceOfTechnics = getAll().stream().mapToInt(x -> x.price).sum();
        if (priceOfTechnics >= LIMIT_TYPE_PRICE) {
            invoiceType = InvoiceType.WHOLESALE;
        }
    }
}
