package com.fedorenko.action;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Actions {
    CREATE("Create new invoice", new CreateAction()),
    FIND("Find invoice", new FindAction()),
    DELETE("Delete invoice", new DeleteAction()),
    SHOW_ALL("Show all invoices", new ShowAllAction()),
    STATISTICS("Show statistics", new ShowStatisticsAction()),
    EXIT("End the program", new ExitAction());
    private final String name;
    private final Action action;
    Actions(String name, Action action) {
        this.name = name;
        this.action = action;
    }
    public void execute() {
        action.execute();
    }

    public static String[] mapToNames() {
        return Arrays.stream(Actions.values())
                .map(Actions::getName)
                .toArray(String[]::new);
    }
}
