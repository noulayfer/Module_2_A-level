package com.fedorenko.action;

import com.fedorenko.action.Action;

public class ExitAction implements Action {
    @Override
    public void execute() {
        System.out.println("Program is finished");
        System.exit(0);
    }
}
