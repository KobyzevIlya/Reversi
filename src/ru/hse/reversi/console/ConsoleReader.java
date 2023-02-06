package ru.hse.reversi.console;

import java.util.Scanner;

public class ConsoleReader {
    private ConsoleReader() {}

    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
