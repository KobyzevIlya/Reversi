package ru.hse.reversi.game;

import java.util.ArrayDeque;
import java.util.Deque;
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

    private Deque<Field> fields;
    
    private Player whitePlayer; // only white player may be Computer
    private Player blackPlayer;

    public newReversi() {
        field = new Field();
        observer = new Observer(field, getTurn());
        fields = new ArrayDeque<>();

        //whitePlayer = new Computer(3, this);
        whitePlayer = new Human(null, null);
        blackPlayer = new Human(null, null);
    }

    public newReversi(int bestScore) {
        this();
        setBestScore(bestScore);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void newObserver() {
        observer = new Observer(field, turn);
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

    public void addFieldToHistory(Field field) {
        fields.push(field);
    }

    public Field popLastFieldFromHistory() {
        return fields.pop();
    }

    public boolean isFieldsHistoryEmpty() {
        return fields.isEmpty();
    }
}
