package ru.hse.reversi.commands;

import ru.hse.reversi.console.ConsoleReader;
import ru.hse.reversi.game.Observer;
import ru.hse.reversi.console.ConsolePrinter;

public class ConsoleCommandGrabber implements CommandGrabber {
    @Override
    public Command getCommand() {
        String command = ConsoleReader.getCommand();

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

        return ConsoleReader.getCommand();
    }

    @Override
    public int getGameMode() {
        ConsolePrinter.printModeInfo();

        while (true) {
            String command = ConsoleReader.getCommand();
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
