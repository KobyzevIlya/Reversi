package ru.hse.reversi.game;

import ru.hse.reversi.field.CharMatrix;
import ru.hse.reversi.field.Field;
import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Observer {
    private final CharMatrix currentField;
    private final Map<Character, Boolean> blackOrWhite;
    private final boolean turn;
    private final ArrayList<IntegerPair> possibleMoves;
    private int blackScore = 0;
    private int whiteScore = 0;
    private int freeCells = 0;

    Observer(Field field, boolean turn) {
        possibleMoves = new ArrayList<>();
        blackOrWhite = new HashMap<>();
        blackOrWhite.put(FiledSymbols.BLACK, true);
        blackOrWhite.put(FiledSymbols.WHITE, false);

        currentField = field.getField();
        calculatePoints();

        this.turn = turn;
        findPossibleMoves();
    }

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
                    if (!possibleMoves.contains(cellMove)) possibleMoves.add(cellMove);
                }

            }
        }
    }

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

    private double cellsAroundCost(int row, int column, int i, int j, int step) {
        double cost = 0;

        if (!isOutOfField(row + i * step, column + j * step) && isEnemy(row + i * (step - 1), column + j * (step - 1)) &&
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

    public IntegerPair getScore() {
        return new IntegerPair(blackScore, whiteScore);
    }

    public boolean isEndOfGame() {
        return freeCells == 0;
    }

    public CharMatrix getCurrentField() {
        return currentField.clone();
    }

    private boolean mapHasPair(Map<Character, Boolean> map, Character key, Boolean value) {
        return map.containsKey(key) && map.get(key).equals(value);
    }

    private boolean isOutOfField(int row, int column) {
        return row < 0 || row > 7 || column < 0 || column > 7;
    }

    private boolean isEnemy(int row, int column) {
        if (isOutOfField(row, column)) return false;
        if (!blackOrWhite.containsKey(currentField.getElementAt(row, column))) return false;

        return blackOrWhite.get(currentField.getElementAt(row, column)) == !turn;
    }

    private boolean isFreeCell(int row, int column, CharMatrix currentField) {
        if (isOutOfField(row, column)) return false;

        return currentField.getElementAt(row, column) == FiledSymbols.SPACE;
    }

    private boolean isCorner(int row, int column) {
        if (isOutOfField(row, column)) return false;

        return (row == 0 && column == 0) || (row == 0 && column == 7) || (row == 7 && column == 0) || (row == 7 && column == 7);
    }

    private boolean isOnEdge(int row, int column) {
        if (isOutOfField(row, column)) return false;

        return (row == 0 && column > 0 && column < 7) || (row == 7 && column > 0 && column < 7)
                || (row > 0 && row < 7 && column == 0) || (row > 0 && row < 7 && column == 7);
    }

    public List<IntegerPair> getPossibleMoves() {
        return possibleMoves;
    }

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
