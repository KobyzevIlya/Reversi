package ru.hse.reversi.game;

import ru.hse.reversi.field.CharMatrix;
import ru.hse.reversi.field.Field;
import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing an observer for the game board.
 */
public class Observer {
    private final CharMatrix currentField;
    private final Map<Character, Boolean> blackOrWhite;
    private final boolean turn;
    private final ArrayList<IntegerPair> possibleMoves;
    private int blackScore = 0;
    private int whiteScore = 0;
    private int freeCells = 0;

    /**
     * Constructor for the Observer class.
     * 
     * @param field the game board to observe
     * @param turn  the player's turn to move
     */
    public Observer(Field field, boolean turn) {
        possibleMoves = new ArrayList<>();
        blackOrWhite = new HashMap<>();
        blackOrWhite.put(FiledSymbols.BLACK, true);
        blackOrWhite.put(FiledSymbols.WHITE, false);

        currentField = field.getField();
        calculatePoints();

        this.turn = turn;
        findPossibleMoves();
    }

    /**
     * Method to find all possible moves for the current player.
     */
    private void findPossibleMoves() {
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                char currentChar = currentField.getElementAt(row, column);
                if (mapHasPair(blackOrWhite, currentChar, turn)) {
                    possibleMovesCellsAround(row, column);
                }
            }
        }
    }

    /**
     * Method to find all possible moves around a cell.
     * 
     * @param row    the row of the cell
     * @param column the column of the cell
     */
    private void possibleMovesCellsAround(int row, int column) {
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                int step = 1;

                if (!isEnemy(row + i, column + j)) {
                    continue;
                }

                while (isEnemy(row + i * step, column + j * step)) {
                    ++step;
                }

                if (isFreeCell(row + i * step, column + j * step, currentField)) {
                    IntegerPair cellMove = new IntegerPair(row + i * step, column + j * step);
                    if (!possibleMoves.contains(cellMove))
                        possibleMoves.add(cellMove);
                }

            }
        }
    }

    /**
     * Method to change the game board according to a player's move.
     * 
     * @param move  the player's move
     * @param field the game board to modify
     */
    public void changeField(IntegerPair move, Field field) {
        int row = move.getFirst();
        int column = move.getSecond();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                int step = 1;

                if (!isEnemy(row + i, column + j)) {
                    continue;
                }

                while (isEnemy(row + i * step, column + j * step)) {
                    ++step;
                }

                if (!isOutOfField(row + i * step, column + j * step)) {
                    if (!isFreeCell(row + i * step, column + j * step, field.getField())) {
                        char player = turn ? FiledSymbols.BLACK : FiledSymbols.WHITE;

                        while (step != 0) {
                            --step;
                            IntegerPair coordinates = new IntegerPair(row + i * step, column + j * step);
                            field.setCell(coordinates, player);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * Finds the cost of enemy cells surrounding a given set of coordinates.
     * 
     * @param coordinates the coordinates to check
     * @return the cost of the enemy cells surrounding the given coordinates
     */
    public double findEnemyCellsCost(IntegerPair coordinates) {
        int row = coordinates.getFirst();
        int column = coordinates.getSecond();

        double cost = 0;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                int step = 1;

                if (!isEnemy(row + i, column + j)) {
                    continue;
                }

                while (isEnemy(row + i * step, column + j * step)) {
                    ++step;
                }

                cost += cellsAroundCost(row, column, i, j, step);
            }
        }

        return cost;
    }

    /**
     * 
     * Calculates the cost of the cells surrounding a given set of coordinates,
     * taking into account the number of steps
     * 
     * away from the original coordinates.
     * 
     * @param row    the row of the original coordinates
     * @param column the column of the original coordinates
     * @param i      the row direction of the surrounding cell
     * @param j      the column direction of the surrounding cell
     * @param step   the number of steps away from the original coordinates
     * @return the cost of the cells surrounding the original coordinates
     */
    private double cellsAroundCost(int row, int column, int i, int j, int step) {
        double cost = 0;

        if (!isOutOfField(row + i * step, column + j * step) && isEnemy(row + i * (step - 1), column + j * (step - 1))
                &&
                mapHasPair(blackOrWhite, currentField.getElementAt(row + i * step, column + j * step), turn)) {
            --step;
            while (step > 0) {
                if (isOnEdge(row + i * step, column + j * step)) {
                    cost += 2;
                } else {
                    cost += 1;
                }
                --step;
            }
            if (isOnEdge(row, column)) {
                cost += 0.4;
            } else if (isCorner(row, column)) {
                cost += 0.8;
            }
        }

        return cost;
    }

    /**
     * 
     * Calculates the number of free, black, and white cells on the current game
     * board and sets the corresponding instance
     * variables.
     */
    private void calculatePoints() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                switch (currentField.getElementAt(i, j)) {
                    case FiledSymbols.SPACE -> ++freeCells;
                    case FiledSymbols.BLACK -> ++blackScore;
                    case FiledSymbols.WHITE -> ++whiteScore;
                }
            }
        }
    }

    /**
     * 
     * Returns the current scores for black and white players.
     * 
     * @return the current scores as an IntegerPair with the first element
     *         representing black score and the second element representing white
     *         score.
     */
    public IntegerPair getScore() {
        return new IntegerPair(blackScore, whiteScore);
    }

    /**
     * 
     * Checks if the game has ended.
     * 
     * @return true if there are no free cells left on the field, false otherwise.
     */
    public boolean isEndOfGame() {
        return freeCells == 0;
    }

    /**
     * 
     * Returns a clone of the current game field.
     * 
     * @return a CharMatrix object representing the current game field.
     */
    public CharMatrix getCurrentField() {
        return currentField.clone();
    }

    /**
     * 
     * Checks if the given map contains a given key-value pair.
     * 
     * @param map   the map to check.
     * @param key   the key to look for.
     * @param value the value to look for.
     * @return true if the map contains the given key-value pair, false otherwise.
     */
    private boolean mapHasPair(Map<Character, Boolean> map, Character key, Boolean value) {
        return map.containsKey(key) && map.get(key).equals(value);
    }

    /**
     * 
     * Checks if the given row and column values are outside of the field
     * boundaries.
     * 
     * @param row    the row index to check.
     * @param column the column index to check.
     * @return true if the given row and column values are outside of the field
     *         boundaries, false otherwise.
     */
    private boolean isOutOfField(int row, int column) {
        return row < 0 || row > 7 || column < 0 || column > 7;
    }

    /**
     * 
     * Checks if the cell at the given row and column indices belongs to the enemy.
     * 
     * @param row    the row index to check.
     * @param column the column index to check.
     * @return true if the cell at the given row and column indices belongs to the
     *         enemy, false otherwise.
     */
    private boolean isEnemy(int row, int column) {
        if (isOutOfField(row, column))
            return false;
        if (!blackOrWhite.containsKey(currentField.getElementAt(row, column)))
            return false;

        return blackOrWhite.get(currentField.getElementAt(row, column)) == !turn;
    }

    /**
     * 
     * Checks if the cell at the given row and column indices is empty.
     * 
     * @param row          the row index to check.
     * @param column       the column index to check.
     * @param currentField the CharMatrix object representing the current game
     *                     field.
     * @return true if the cell at the given row and column indices is empty, false
     *         otherwise.
     */
    private boolean isFreeCell(int row, int column, CharMatrix currentField) {
        if (isOutOfField(row, column))
            return false;

        return currentField.getElementAt(row, column) == FiledSymbols.SPACE;
    }

    /**
     * 
     * Checks if the cell at the given row and column indices is in a corner of the
     * field.
     * 
     * @param row    the row index to check.
     * @param column the column index to check.
     * @return true if the cell at the given row and column indices is in a corner
     *         of the field, false otherwise.
     */
    private boolean isCorner(int row, int column) {
        if (isOutOfField(row, column))
            return false;

        return (row == 0 && column == 0) || (row == 0 && column == 7) || (row == 7 && column == 0)
                || (row == 7 && column == 7);
    }

    /**
     * 
     * Checks if a cell is on the edge of the board.
     * 
     * @param row    the row of the cell
     * @param column the column of the cell
     * @return true if the cell is on the edge of the board, false otherwise
     */
    private boolean isOnEdge(int row, int column) {
        if (isOutOfField(row, column))
            return false;

        return (row == 0 && column > 0 && column < 7) || (row == 7 && column > 0 && column < 7)
                || (row > 0 && row < 7 && column == 0) || (row > 0 && row < 7 && column == 7);
    }

    /**
     * 
     * Returns a list of possible moves for the current turn.
     * 
     * @return a list of possible moves for the current turn
     */
    public List<IntegerPair> getPossibleMoves() {
        return possibleMoves;
    }

    /**
     * 
     * Adds the given list of moves to a clone of the given field and returns the
     * resulting field.
     * 
     * @param moves the list of moves to add
     * @param field the field to clone and add the moves to
     * @return the resulting field after the moves have been added
     */
    public static Field addMovesToField(List<IntegerPair> moves, Field field) {
        Field cloneField = new Field();
        cloneField.setField(field.getField());

        char moveLetter = 'a';
        for (var x : moves) {
            cloneField.setCell(x, moveLetter);
            ++moveLetter;
        }

        return cloneField;
    }
}
