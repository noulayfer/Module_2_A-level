package com.fedorenko.action;

import com.fedorenko.service.ShopService;

public interface Action {
    ShopService SHOP_SERVICE = ShopService.getInstance();
    void execute();
}
