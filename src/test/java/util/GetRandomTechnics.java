package util;

import com.fedorenko.model.Technics;
import com.fedorenko.util.StringGenerator;

import java.util.Random;

public class GetRandomTechnics {
    private final static Random RANDOM = new Random();

    public static Technics get() {
        final String series = StringGenerator.randomString();
        final String screenType = StringGenerator.randomString();
        final int price = RANDOM.nextInt();
        return new Technics(series, screenType, price);
    }
}
