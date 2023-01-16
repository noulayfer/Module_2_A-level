package com.fedorenko.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {
    private final String id;
    private final String email;
    private int age;

    public Customer(@NonNull final String id, final String email, final int age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }
}
