package ru.hse.reversi.messages;

import ru.hse.reversi.console.ConsolePrinter;
import ru.hse.reversi.field.Field;
import ru.hse.reversi.game.Observer;
import ru.hse.reversi.game.Reversi;

public class ConsoleMessageHandler implements MessageHandler{
    @Override
    public void gameEnd(Observer observer, Reversi game) {
        ConsolePrinter.printEnd();
        ConsolePrinter.printScore(observer);
        ConsolePrinter.printBestScore(game);
    }

    @Override
    public void skipTurn(boolean turn) {
        ConsolePrinter.printSkipInfo(turn);
    }

    @Override
    public void showField(Field field) {
        ConsolePrinter.printField(field);
    }

    @Override
    public void turnInfo(Reversi game) {
        ConsolePrinter.printTurnInfo(game);
    }

    @Override
    public void info(Reversi game) {
        ConsolePrinter.printInfo(game);
    }

    @Override
    public void score(Observer observer) {
        ConsolePrinter.printScore(observer);
    }

    @Override
    public void bestScore(Reversi game) {
        ConsolePrinter.printBestScore(game);
    }

    @Override
    public void invalidCommand() {
        ConsolePrinter.printInvalidCommand();
    }
}
