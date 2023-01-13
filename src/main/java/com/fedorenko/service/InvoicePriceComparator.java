package com.fedorenko.service;

import com.fedorenko.model.Invoice;
import com.fedorenko.model.Technics;

import java.util.Comparator;

class InvoicePriceComparator implements Comparator<Invoice> {
    @Override
    public int compare(Invoice o1, Invoice o2) {
        return Integer.compare(o2.getAll().stream().mapToInt(Technics::getPrice).sum(),
                o1.getAll().stream().mapToInt(Technics::getPrice).sum());
    }
}
