package com.fedorenko.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Television extends Technics {
    private final int diagonal;
    private final String country;

    public Television(@NonNull final String series, final String screenType,
                      final int price, final int diagonal, final String country) {
        super(series, screenType, price);
        this.diagonal = diagonal;
        this.country = country;
        technicsType = TechnicsType.TELEVISION;
    }

    @Override
    public String toString() {
        return "Television{" +
                "series='" + super.getSeries() + '\'' +
                ", screenType='" + super.getScreenType() + '\'' +
                ", price=" + super.getPrice() +
                ", diagonal=" + diagonal +
                ", country=" + country +
                '}';
    }
}
