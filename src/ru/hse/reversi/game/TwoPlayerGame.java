package ru.hse.reversi.game;

/**
 * Abstract class for two-player games.
 */
public abstract class TwoPlayerGame {
    protected boolean turn = true;
    protected boolean gameStarted = false;
    protected int bestScore = 0;

    /**
     * Returns the current turn of the game.
     *
     * @return true if it is black player's turn, false if it is white player's turn
     */
    public boolean getTurn() {
        return turn;
    }

    /**
     * Sets the current turn of the game.
     *
     * @param newTurn true if it is player one's turn, false if it is player two's turn
     */
    public void setTurn(boolean newTurn) {
        turn = newTurn;
    }

    /**
     * Returns whether the game has started.
     *
     * @return true if the game has started, false otherwise
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Sets the game as not started.
     */
    public void setGameNotStarted() {
        gameStarted = false;
    }

    /**
     * Sets the game as started.
     */
    public void setGameStarted() {
        gameStarted = true;
    }

    /**
     * Returns the best score of the game.
     *
     * @return the best score as an integer
     */
    public int getBestScore() {
        return bestScore;
    }

    /**
     * Sets the best score of the game.
     *
     * @param score the new best score as an integer
     */
    public void setBestScore(int score) {
        bestScore = score;
    }
}

