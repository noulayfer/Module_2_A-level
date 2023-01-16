package com.fedorenko.service;

import com.fedorenko.model.Customer;
import com.fedorenko.util.StringGenerator;

import java.util.Random;

public class PersonService {
    private final static Random RANDOM = new Random();
    public Customer generateCustomer() {
        final String domain = "@gmail.com";
        int maxAge = 80;

        final String id = StringGenerator.randomString();
        final String email = StringGenerator.randomString() + domain;
        final int age = RANDOM.nextInt(maxAge + 1);

        return new Customer(id, email, age);
    }
}
