package com.fedorenko.action;

public class ShowStatisticsAction implements Action {
    public void execute() {
        SHOP_SERVICE.showAllStatistics();
    }
}
