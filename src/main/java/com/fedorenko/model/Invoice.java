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
    private final List<Technics> listOfTechnics = new ArrayList<>();
    private final Customer customer;
    private InvoiceType invoiceType;
    private static final int LIMIT_TYPE_PRICE = 3000;

    public Invoice(@NonNull final Customer customer) {
        this.customer = customer;
        invoiceType = InvoiceType.RETAIL;
    }

    public void addProduct(@NonNull final Technics technics) {
        listOfTechnics.add(technics);
        wholesaleProcess();
    }

    public List<Technics> getInvoiceTechnics() {
        return listOfTechnics;
    }

    @Override
    public String toString() {
        return "Invoice{" + " TIME[" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "] USER_DATA[customer=" + customer +
                "] INVOICE_DATA" + getInvoiceTechnics() +
                '}';
    }

    private void wholesaleProcess() {
        int priceOfTechnics = getInvoiceTechnics().stream().mapToInt(Technics::getPrice).sum();
        if (priceOfTechnics >= LIMIT_TYPE_PRICE) {
            invoiceType = InvoiceType.WHOLESALE;
        }
    }
}
