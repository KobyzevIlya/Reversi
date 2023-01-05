package ru.hse.reversi.game;

import ru.hse.reversi.field.CharMatrix;

import java.util.Deque;

public class Reversi extends TwoPlayerGame {
    private final Deque<CharMatrix> fields = null;
    private int gameMode;

    public static void startGame() {

    }

    public boolean isFirstMove() {
        return fields.isEmpty();
    }

    public int getGameMode() {
        return gameMode;
    }
}
