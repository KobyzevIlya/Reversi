package ru.hse.reversi.commands;

import ru.hse.reversi.game.Observer;

public interface CommandGrabber {
    Command getCommand();

    default Command startCommand() {
        return Command.START;
    }

    default Command exitCommand() {
        return Command.EXIT;
    }

    default Command moveCommand() {
        return Command.MOVE;
    }

    default Command scoreCommand() {
        return Command.SCORE;
    }

    default Command backCommand() {
        return Command.BACK;
    }

    default Command falseCommand() {
        return Command.FALSE;
    }

    String getMove(Observer observer);

    int getGameMode();
}
