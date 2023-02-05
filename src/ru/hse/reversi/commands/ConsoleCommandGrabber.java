package ru.hse.reversi.commands;

import ru.hse.reversi.game.Observer;
import ru.hse.reversi.console.ConsolePrinter;

import java.util.Scanner;

public class ConsoleCommandGrabber implements CommandGrabber {
    @Override
    public Command getCommand() {
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();

        switch (command) {
            case "/s" -> {
                return startCommand();
            }
            case "/e" -> {
                return exitCommand();
            }
            case "/m" -> {
                return moveCommand();
            }
            case "/p" -> {
                return scoreCommand();
            }
            case "/b" -> {
                return backCommand();
            }
        }

        return falseCommand();
    }

    @Override
    public String getMove(Observer observer) {
        ConsolePrinter.printPositions(observer);

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public int getGameMode() {
        Scanner scanner = new Scanner(System.in);
        String command;

        ConsolePrinter.printModeInfo();

        while (true) {
            command = scanner.nextLine();
            switch (command) {
                case "1" -> {
                    return 1;
                }
                case "2" -> {
                    return 2;
                }
                case "3" -> {
                    return 3;
                }
                case "0" -> {
                    return 0;
                }
            }
            ConsolePrinter.printInvalidCommand();
        }
    }
}
