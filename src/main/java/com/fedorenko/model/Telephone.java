package com.fedorenko.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class Telephone extends Technics {
    private final String model;

    public Telephone(@NonNull final String series, final String screenType, final int price, final String model) {
        super(series, screenType, price);
        this.model = model;
        technicsType = TechnicsType.TELEPHONE;
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "series='" + super.getSeries() + '\'' +
                ", screenType='" + super.getScreenType() + '\'' +
                ", price=" + super.getPrice() +
                ", technicsType=" + technicsType +
                ", model=" + model +
                '}';
    }
}
