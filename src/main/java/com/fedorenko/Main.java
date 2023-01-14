package com.fedorenko;

import com.fedorenko.action.Actions;
import com.fedorenko.model.Invoice;
import com.fedorenko.repositry.InvoiceRepository;
import com.fedorenko.service.ShopService;
import com.fedorenko.util.UserInput;

public class Main {
    public static void main(String[] args) {
        final ShopService shopService = ShopService.getInstance();
        final InvoiceRepository invoiceRepository = InvoiceRepository.getInstance();
        while(invoiceRepository.getAll().size() < 15) {
            shopService.dataToInvoice("data.csv");
        }

        while (true) {
            int a = UserInput.menu(Actions.mapToNames());
            Actions.values()[a].execute();
        }
    }
}
