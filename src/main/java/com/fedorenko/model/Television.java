package com.fedorenko.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
                "series='" + series + '\'' +
                ", screenType='" + screenType + '\'' +
                ", price=" + price +
                ", diagonal=" + diagonal +
                ", country=" + country +
                '}';
    }
}
