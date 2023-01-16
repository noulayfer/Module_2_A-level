package com.fedorenko.repositry;

import com.fedorenko.model.Invoice;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {
    private final static List<Invoice> INVOICE_LIST = new ArrayList<>();
    private static InvoiceRepository invoiceRepository;

    private InvoiceRepository() {
    }

    public static InvoiceRepository getInstance() {
        if (invoiceRepository == null) {
            invoiceRepository = new InvoiceRepository();
        }
        return invoiceRepository;
    }

    public void clear() {
        INVOICE_LIST.clear();
    }

    public void addInvoice(@NonNull final Invoice invoice) {
        INVOICE_LIST.add(invoice);
    }

    public List<Invoice> getAll() {
        return INVOICE_LIST;
    }
}




