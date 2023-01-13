package com.fedorenko.service;


import com.fedorenko.model.*;
import com.fedorenko.repositry.InvoiceRepository;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class ShopService {
    private final static Random RANDOM = new Random();
    private static final InvoiceRepository INVOICE_REPOSITORY = InvoiceRepository.getInstance();

    private static ShopService shopService;

    private static final InvoiceAgeComparator INVOICE_AGE_COMPARATOR = new InvoiceAgeComparator();
    private static final InvoiceAmountComparator INVOICE_AMOUNT_COMPARATOR = new InvoiceAmountComparator();
    private static final InvoicePriceComparator INVOICE_PRICE_COMPARATOR = new InvoicePriceComparator();

    private ShopService() {
    }

    public static ShopService getInstance() {
        if (shopService == null) {
            shopService = new ShopService();
        }
        return shopService;
    }

    public List<Invoice> getAllInvoices() {
        return INVOICE_REPOSITORY.getAll();
    }

    public Invoice dataToInvoice(@NonNull final String filename) {
        final String data = readFromFile(filename);
        final String[] linesOfData = data.split("\n");
        final String[] columnsOfKeys = linesOfData[0].split(",");
        final int amountOfTechnics = getRandomIntArr(1, 1, 6)[0];
        final int[] indexesOfTechnics = getRandomIntArr(amountOfTechnics, 1, linesOfData.length - 1);
        final Map<String, String> technicsMap = new HashMap<>();
        final Invoice invoice = new Invoice(new PersonService().generateCustomer());
        for (int index : indexesOfTechnics) {
            for (int i = 0; i < columnsOfKeys.length; i++) {
                String key = columnsOfKeys[i];
                String value = linesOfData[index].split(",")[i];
                if (value.isBlank()) {
                    try {
                        throw new UnableToReadDataException();
                    } catch (UnableToReadDataException e) {
                        System.out.println("You have illegal data in your csv file");
                    }
                }
                technicsMap.put(key, value);
            }
            invoice.addProduct(randomTechnics(technicsMap));
        }
        INVOICE_REPOSITORY.addInvoice(invoice);
        return invoice;
    }

    public Map<TechnicsType, Long> getTypeInfo(@NonNull List<Invoice> invoiceList) {
        return invoiceList.stream()
                .flatMap(x -> x.getInvoiceTechnics().stream())
                .collect(Collectors.groupingBy(Technics::getTechnicsType, Collectors.counting()));
    }

    public int minInvoice(@NonNull final List<Invoice> invoiceList) {
        return invoiceList.stream()
                .min(Comparator.comparingInt(x -> x.getInvoiceTechnics().stream().mapToInt(Technics::getPrice).sum()))
                .stream().peek(invoice -> System.out.println(invoice.getCustomer()))
                .flatMap(x -> x.getInvoiceTechnics().stream())
                .mapToInt(Technics::getPrice).sum();
    }

    public int allInvoiceSum(@NonNull final List<Invoice> invoiceList) {
        return invoiceList.stream()
                .flatMap(x -> x.getInvoiceTechnics().stream())
                .mapToInt(Technics::getPrice).sum();
    }

    public int retailInvoices(@NonNull final List<Invoice> invoiceList) {
        return (int) invoiceList.stream()
                .filter(x -> x.getInvoiceType().equals(InvoiceType.RETAIL))
                .count();
    }

    public List<Invoice> oneTypeInvoices(@NonNull final List<Invoice> invoiceList) {
        return invoiceList.stream()
                .filter(y -> 1 == y.getInvoiceTechnics().stream()
                        .map(Technics::getTechnicsType)
                        .distinct()
                        .toArray()
                        .length).collect(Collectors.toList());
    }

    public List<Invoice> firstThreeInvoices(@NonNull final List<Invoice> invoiceList) {
        return invoiceList.stream()
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Invoice> lessThanEighteenInvoice(@NonNull final List<Invoice> invoiceList) {
        return invoiceList.stream()
                .filter(x -> x.getCustomer().getAge() < 18)
                .peek(x -> x.setInvoiceType(InvoiceType.LOW_AGE))
                .collect(Collectors.toList());
    }

    public List<Invoice> sortInvoices(@NonNull final List<Invoice> invoiceList) {
        return invoiceList.stream().sorted(
                INVOICE_AGE_COMPARATOR
                        .thenComparing(INVOICE_AMOUNT_COMPARATOR)
                        .thenComparing(INVOICE_PRICE_COMPARATOR)
        ).collect(Collectors.toList());
    }
    private Technics randomTechnics(@NonNull final Map<String, String> mapWithData) {
        final String series = mapWithData.get("series");
        final String screenType = mapWithData.get("screen type");
        final int price = Integer.parseInt(mapWithData.get("price"));
        final String type = mapWithData.get("type");
        if (type.equalsIgnoreCase("Telephone")) {
            return createTelephone(mapWithData, series, screenType, price);
        } else if (type.equalsIgnoreCase("Television")) {
            return createTelevision(mapWithData, series, screenType, price);
        }
        throw new IllegalArgumentException();
    }

    private String readFromFile(@NonNull final String fileName) {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        StringBuilder result = new StringBuilder();
        try (final InputStream resourceAsStream = contextClassLoader.getResourceAsStream(fileName);
             BufferedReader bf = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            String strAppend;
            while ((strAppend = bf.readLine()) != null) {
                result.append(strAppend + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }

    private Telephone createTelephone(@NonNull final Map<String, String> mapWithData, final String series,
                                      final String screenType, final int price) {
        final String model = mapWithData.get("com/fedorenko/model");
        return new Telephone(series, screenType, price, model);
    }

    private Television createTelevision(@NonNull final Map<String, String> mapWithData, final String series,
                                        final String screenType, final int price) {
        final int diagonal = Integer.parseInt(mapWithData.get("diagonal"));
        final String country = mapWithData.get("country");
        return new Television(series, screenType, price, diagonal, country);
    }

    private int[] getRandomIntArr(final int arrayLength, final int min, final int max) {
        return RANDOM.ints(arrayLength, min, max).toArray();
    }
}
