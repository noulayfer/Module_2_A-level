package com.fedorenko;

import com.fedorenko.action.Actions;
import com.fedorenko.util.UserInput;

public class Main {
    public static void main(String[] args) {
        while (true) {
            int a = UserInput.menu(Actions.mapToNames());
            Actions.values()[a].execute();
        }
    }
}
