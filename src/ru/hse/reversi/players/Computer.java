package ru.hse.reversi.players;

import ru.hse.reversi.commands.CommandGrabber;
import ru.hse.reversi.field.Field;
import ru.hse.reversi.game.Observer;
import ru.hse.reversi.game.Reversi;
import ru.hse.reversi.messages.MessageHandler;
import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

import java.util.ArrayList;

public class Computer implements Player {
    private final int mode;
    private final CommandGrabber commandGrabber;
    private final MessageHandler messageHandler;
    private final Reversi game;

    public Computer(int gameMode, CommandGrabber commandGrabber, MessageHandler messageHandler, Reversi game) {
        this.mode = gameMode;
        this.commandGrabber = commandGrabber;
        this.messageHandler = messageHandler;
        this.game = game;
    }

    @Override
    public IntegerPair makeMove(Observer observer) {
        if (mode == 3) {
            return professionalMove(observer);
        }
        return rookieMove(observer);
    }

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

        messageHandler.chosenLetter((char) ('a' + possibleMoves.indexOf(move)));
        return move;
    }

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

        messageHandler.chosenLetter((char) ('a' + possibleMoves.indexOf(move)));
        return move;
    }
}
