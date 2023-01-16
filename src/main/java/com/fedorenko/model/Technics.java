package com.fedorenko.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Technics {
    private final String series;
    private final String screenType;
    private int price;
    TechnicsType technicsType;

    public Technics(@NonNull final String series, final String screenType, final int price) {
        this.series = series;
        this.screenType = screenType;
        this.price = price;
    }
}
