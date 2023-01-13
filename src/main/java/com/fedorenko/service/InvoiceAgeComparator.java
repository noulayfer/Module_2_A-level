package com.fedorenko.service;

import com.fedorenko.model.Invoice;

import java.util.Comparator;

class InvoiceAgeComparator implements Comparator<Invoice> {
    @Override
    public int compare(Invoice o1, Invoice o2) {
        return Integer.compare(o2.getCustomer().getAge(), o1.getCustomer().getAge());
    }
}
