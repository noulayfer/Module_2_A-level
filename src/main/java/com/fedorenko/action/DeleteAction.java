package com.fedorenko.action;

import com.fedorenko.model.Invoice;
import com.fedorenko.util.UserInput;

public class DeleteAction implements Action {
    @Override
    public void execute() {
        int indexOfInvoice = UserInput.getInt("Choose what invoice you want to delete?");
        try {
            Invoice invoice = SHOP_SERVICE.getAllInvoices().remove(indexOfInvoice);
            System.out.println(invoice + " was deleted");
        } catch (Exception e) {
            System.out.println("There is not such invoice");
        }
    }
}
