package ru.hse.reversi.messages;

import ru.hse.reversi.field.Field;
import ru.hse.reversi.game.Observer;
import ru.hse.reversi.game.Reversi;

public interface MessageHandler {
    void gameEnd(Observer observer, Reversi game);

    void skipTurn(boolean turn);

    void showField(Field field);

    void turnInfo(Reversi game);

    void startExitInfo();

    void commandsInfo();

    void backInfo();

    void score(Observer observer);

    void bestScore(Reversi game);

    void invalidCommand();

    void chosenLetter(char letter);
}
