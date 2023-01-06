package ru.hse.reversi.commands;

import ru.hse.reversi.game.Observer;
import ru.hse.reversi.game.Reversi;
import ru.hse.reversi.console.ConsolePrinter;

import java.util.Scanner;

public class ConsoleCommandGrabber implements CommandGrabber {
    @Override
    public Command getCommand() {
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();

        switch (command) {
            case "/s" -> {
                startCommand();
            }
            case "/e" -> {
                exitCommand();
            }
            case "/m" -> {
                moveCommand();
            }
            case "/p" -> {
                scoreCommand();
            }
            case "/b" -> {
                backCommand();
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
