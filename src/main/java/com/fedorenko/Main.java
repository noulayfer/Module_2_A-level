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
        for (int i = 0; i < 15; i++) {
            shopService.dataToInvoice("data.csv");
        }
        System.out.println("~".repeat(10));
        System.out.println(shopService.allInvoiceSum(invoiceRepository.getAll()));
        System.out.println("~".repeat(10));
        System.out.println(shopService.firstThreeInvoices(invoiceRepository.getAll()));
        System.out.println("~".repeat(10));
        System.out.println(shopService.getTypeInfo(invoiceRepository.getAll()));
        System.out.println("~".repeat(10));
        System.out.println(shopService.lessThanEighteenInvoice(invoiceRepository.getAll()));
        System.out.println("~".repeat(10));
        System.out.println(shopService.minInvoice(invoiceRepository.getAll()));
        System.out.println("~".repeat(10));
        System.out.println(shopService.oneTypeInvoices(invoiceRepository.getAll()));
        System.out.println("~".repeat(10));
        System.out.println(shopService.retailInvoices(invoiceRepository.getAll()));
        System.out.println("~".repeat(10));
        System.out.println(shopService.sortInvoices(invoiceRepository.getAll()));

        while (true) {
            int a = UserInput.menu(Actions.mapToNames());
            Actions.values()[a].execute();
        }
    }
}
