package ru.hse.reversi.commands;

import ru.hse.reversi.game.Observer;
import ru.hse.reversi.game.Reversi;

public interface CommandHandler {
    Command getCommand(Reversi game);

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

    public String getMove(Observer observer);

    public int getGameMode();
}
