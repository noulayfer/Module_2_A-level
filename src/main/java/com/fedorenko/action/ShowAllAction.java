package com.fedorenko.action;

public class ShowAllAction implements Action {
    @Override
    public void execute() {
        System.out.println(SHOP_SERVICE.getAllInvoices());
    }
}
