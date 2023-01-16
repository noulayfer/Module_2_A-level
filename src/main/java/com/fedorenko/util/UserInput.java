package com.fedorenko.util;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UserInput {
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    @SneakyThrows
    public static int menu(String[] names) {
        int userChoice = -1;
        do {
            System.out.println("Choose what you need");
            for (int i = 0; i < names.length; i++) {
                System.out.println(i + " " + names[i]);
            }
            final String userInput = BUFFERED_READER.readLine();
            if (!StringUtils.isNumeric(userInput)) {
                continue;
            }
            userChoice = Integer.parseInt(userInput);
        } while (userChoice < 0 || userChoice >= names.length);
        return userChoice;
    }

    @SneakyThrows
    public static int getInt(String option) {
        String userInput;
        int userInputNum = -1;
        do {
            System.out.println(option);
            userInput = BUFFERED_READER.readLine();
            if (StringUtils.isNumeric(userInput)) {
                userInputNum = Integer.parseInt(userInput);
            }
        } while (!StringUtils.isNumeric(userInput));
        return userInputNum;
    }
}
