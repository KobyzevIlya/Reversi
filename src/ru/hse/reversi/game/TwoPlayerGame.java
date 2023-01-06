package ru.hse.reversi.game;

public abstract class TwoPlayerGame {
    protected boolean turn = true;
    protected boolean gameStarted = false;
    protected int bestScore = 0;

    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean newTurn) {
        turn = newTurn;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameNotStarted() {
        gameStarted = false;
    }

    public void setGameStarted() {
        gameStarted = true;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int score) {
        bestScore = score;
    }
}
