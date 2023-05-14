package ru.hse.reversi.game;

import java.util.ArrayDeque;
import java.util.Deque;

import ru.hse.reversi.field.Field;
import ru.hse.reversi.players.Computer;
import ru.hse.reversi.players.Player;
import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

/**
 * 
 * Reversi class represents a two-player game of Reversi/Othello.
 * It extends the TwoPlayerGame abstract class and implements its methods.
 */
public class Reversi extends TwoPlayerGame {
    private Field field;
    private Observer observer;

    private Deque<Field> fields;

    private Player whitePlayer; // only white player may be Computer

    private String gameMode;

    /**
     * Constructs a new Reversi game with the specified game mode.
     * 
     * @param gameMode the game mode of the Reversi game
     */
    public Reversi(String gameMode) {
        field = new Field();
        observer = new Observer(field, getTurn());
        fields = new ArrayDeque<>();

        if (gameMode == "Human") {
            this.gameMode = gameMode;
        } else if (gameMode == "Easy") {
            whitePlayer = new Computer(2, this);
            this.gameMode = gameMode;
        } else if (gameMode == "Hard") {
            whitePlayer = new Computer(3, this);
            this.gameMode = gameMode;
        }
    }

    /**
     * Constructs a new Reversi game with the specified game mode and best score.
     * 
     * @param gameMode  the game mode of the Reversi game
     * @param bestScore the best score achieved in the Reversi game
     */
    public Reversi(String gameMode, int bestScore) {
        this(gameMode);
        setBestScore(bestScore);
    }

    /**
     * Returns the playing field for the Reversi game.
     * 
     * @return the playing field for the Reversi game
     */
    public Field getField() {
        return field;
    }

    /**
     * Sets the playing field for the Reversi game.
     * 
     * @param field the new playing field for the Reversi game
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * Creates a new observer for the Reversi game.
     */
    public void newObserver() {
        observer = new Observer(field, turn);
    }

    /**
     * Returns the observer for the Reversi game.
     * 
     * @return the observer for the Reversi game
     */
    public Observer getObserver() {
        return observer;
    }

    /**
     * Makes a move in the Reversi game at the specified location.
     * 
     * @param move the location to make the move at
     */
    public void makeMove(IntegerPair move) {
        field.setCell(move, getTurn() ? FiledSymbols.BLACK : FiledSymbols.WHITE);
        observer.changeField(move, field);

        setTurn(!turn);
        observer = new Observer(field, getTurn());
    }

    /**
     * Returns whether the current player in the Reversi game is a computer.
     * 
     * @return true if the current player is a computer, false otherwise
     */
    public boolean isCurrentComputer() {
        return whitePlayer instanceof Computer;
    }

    /**
     * Makes a move for the computer player in the Reversi game.
     */
    public void makeComputerMove() {
        if (getObserver().getPossibleMoves().isEmpty()) {
            setTurn(!turn);
            observer = new Observer(field, getTurn());

            return;
        }

        IntegerPair move = whitePlayer.makeMove(observer);
        makeMove(move);
    }

    /**
     * Adds a playing field to the history of the Reversi game.
     * 
     * @param field the playing field to add to the history
     */
    public void addFieldToHistory(Field field) {
        fields.push(field);
    }

    /**
     * Removes and returns the most recent playing field from the history of the
     * Reversi game.
     * 
     * @return the most recent playing field in the history
     */
    public Field popLastFieldFromHistory() {
        return fields.pop();
    }

    /**
     * Returns whether the field history is empty.
     *
     * @return true if the field history is empty, false otherwise
     */
    public boolean isFieldsHistoryEmpty() {
        return fields.isEmpty();
    }

    /**
     * Returns the game mode of the current game.
     *
     * @return the game mode as a String
     */
    public String getGameMode() {
        return gameMode;
    }
}
