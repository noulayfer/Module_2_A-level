package com.fedorenko.action;

import com.fedorenko.action.Action;
import com.fedorenko.model.Invoice;

public class CreateAction implements Action {

    @Override
    public void execute() {
        final Invoice invoice = SHOP_SERVICE.dataToInvoice("data.csv");
        System.out.println("There was create invoice:" + invoice);
    }
}
