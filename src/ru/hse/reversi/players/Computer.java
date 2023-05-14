package ru.hse.reversi.players;

import ru.hse.reversi.field.Field;
import ru.hse.reversi.game.Observer;
import ru.hse.reversi.game.Reversi;
import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

import java.util.ArrayList;

/**
 * 
 * This class represents a computer player for the Reversi game. It implements
 * the Player interface.
 */
public class Computer implements Player {
    private final int mode;
    private final Reversi game;

    /**
     * 
     * Creates a new instance of the Computer class.
     * 
     * @param gameMode the mode of the game (2 for normal, 3 for hard)
     * @param game     the instance of the Reversi game
     */
    public Computer(int gameMode, Reversi game) {
        this.mode = gameMode; // 2 - normal, 3 - hards
        this.game = game;
    }

    /**
     * 
     * This method makes a move for the computer player, based on the current
     * observer.
     * 
     * @param observer the current observer of the game
     * @return an IntegerPair representing the computer's move
     */
    @Override
    public IntegerPair makeMove(Observer observer) {
        if (mode == 3) {
            return professionalMove(observer);
        }
        return rookieMove(observer);
    }

    /**
     * 
     * This method makes a rookie move for the computer player, based on the current
     * observer.
     * 
     * @param observer the current observer of the game
     * 
     * @return an IntegerPair representing the computer's move
     */
    private IntegerPair rookieMove(Observer observer) {
        ArrayList<IntegerPair> possibleMoves = (ArrayList<IntegerPair>) observer.getPossibleMoves();

        double maxProbability = 0;
        IntegerPair move = possibleMoves.get(0);
        for (var x : possibleMoves) {
            double currentCellProbability = observer.findEnemyCellsCost(x);

            if (currentCellProbability > maxProbability) {
                maxProbability = currentCellProbability;
                move = x;
            }
        }

        return move;
    }

    /**
     * 
     * This method makes a professional move for the computer player, based on the
     * current observer.
     * 
     * @param observer the current observer of the game
     * 
     * @return an IntegerPair representing the computer's move
     */
    private IntegerPair professionalMove(Observer observer) {
        ArrayList<IntegerPair> possibleMoves = (ArrayList<IntegerPair>) observer.getPossibleMoves();

        double maxProbability = -100;
        IntegerPair move = possibleMoves.get(0);
        for (var x : possibleMoves) {
            double currenCellProbability = observer.findEnemyCellsCost(x);

            Field nextField = new Field();
            nextField.setField(observer.getCurrentField());

            nextField.setCell(x, game.getTurn() ? FiledSymbols.BLACK : FiledSymbols.WHITE);
            observer.changeField(x, nextField);

            game.setTurn(!game.getTurn());
            Observer nextObserver = new Observer(nextField, game.getTurn());

            double nextMaxProbability = 0;
            for (var y : nextObserver.getPossibleMoves()) {
                double nextCurrentCellProbability = nextObserver.findEnemyCellsCost(y);

                nextMaxProbability = Math.max(nextMaxProbability, nextCurrentCellProbability);
            }

            currenCellProbability -= nextMaxProbability;
            if (currenCellProbability > maxProbability) {
                maxProbability = currenCellProbability;
                move = x;
            }

            game.setTurn(!game.getTurn());
        }

        return move;
    }
}
