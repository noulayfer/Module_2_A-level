package com.fedorenko.action;

import com.fedorenko.model.Invoice;
import com.fedorenko.util.UserInput;

public class FindAction implements Action {

    @Override
    public void execute() {
        int indexOfInvoice = UserInput.getInt("Choose what invoice you want to find?");
        try {
            Invoice invoice = SHOP_SERVICE.getAllInvoices().get(indexOfInvoice);
            System.out.println(invoice);
        } catch (Exception e) {
            System.out.println("There is not such invoice");
        }
    }
}
