package com.fedorenko.action;

public class ShowAllAction implements Action {
    @Override
    public void execute() {
        SHOP_SERVICE.getAll();
    }
}
