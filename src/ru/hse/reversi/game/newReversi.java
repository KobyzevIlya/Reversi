package ru.hse.reversi.game;

import java.util.concurrent.TimeUnit;

import ru.hse.reversi.console.ConsolePrinter;
import ru.hse.reversi.field.Field;
import ru.hse.reversi.game.Observer;
import ru.hse.reversi.players.Computer;
import ru.hse.reversi.players.Human;
import ru.hse.reversi.players.Player;
import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

public class newReversi extends TwoPlayerGame {
    private Field field;
    private Observer observer;
    
    private Player whitePlayer; // only white player may be Computer
    private Player blackPlayer;

    public newReversi() {
        field = new Field();
        observer = new Observer(field, getTurn());

        whitePlayer = new Computer(3, this);
        blackPlayer = new Human(null, null);
    }

    public Field getField() {
        return field;
    }

    public Observer getObserver() {
        return observer;
    }

    public void makeMove(IntegerPair move) {
        field.setCell(move, getTurn() ? FiledSymbols.BLACK : FiledSymbols.WHITE);
        observer.changeField(move, field);

        setTurn(!turn);
        observer = new Observer(field, getTurn());
    }

    public boolean isCurrentComputer() {
        return whitePlayer instanceof Computer;
    }

    public void makeComputerMove() {
        if (getObserver().getPossibleMoves().isEmpty()) {
            setTurn(!turn);
            observer = new Observer(field, getTurn());
            
            return;
        }
        
        IntegerPair move = whitePlayer.makeMove(observer);
        makeMove(move);
    }
}
