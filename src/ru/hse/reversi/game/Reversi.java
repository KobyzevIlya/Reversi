package ru.hse.reversi.game;

import ru.hse.reversi.commands.Command;
import ru.hse.reversi.commands.CommandGrabber;
import ru.hse.reversi.commands.ConsoleCommandGrabber;
import ru.hse.reversi.field.CharMatrix;
import ru.hse.reversi.field.Field;
import ru.hse.reversi.messages.ConsoleMessageHandler;
import ru.hse.reversi.messages.MessageHandler;
import ru.hse.reversi.players.Computer;
import ru.hse.reversi.players.Human;
import ru.hse.reversi.players.Player;
import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

import java.util.ArrayDeque;
import java.util.Deque;

public class Reversi extends TwoPlayerGame {
    private Deque<CharMatrix> fields;
    private int gameMode;
    private Field field;
    private Observer observer;
    private final CommandGrabber commandGrabber; // mb not final?
    private final MessageHandler messageHandler;

    private Player blackPlayer;
    private Player whitePlayer;

    public Reversi() {
        commandGrabber = new ConsoleCommandGrabber();
        messageHandler = new ConsoleMessageHandler();
    }

    public void startGame() {
        Command command = null;
        while (command != Command.EXIT) {
            if (initializeGame()) {
                continue;
            }

            info();

            if (!isGameStarted()) {
                command = commandGrabber.getCommand();

                if (command == Command.EXIT) {
                    continue;
                }

                if (initializeVariables()) {
                    setGameStarted();
                } else {
                    continue;
                }
            }

            if (doMove(observer, field, blackPlayer, whitePlayer)) {
                command = Command.MOVE;
                continue;
            } else {
                info();
                command = commandGrabber.getCommand();
            }

            switch (command) {
                case START -> initializeVariables();
                case SCORE -> {
                    if (!isGameStarted()) {
                        messageHandler.invalidCommand();
                        continue;
                    }
                    messageHandler.score(observer);
                    if (observer.isEndOfGame()) {
                        messageHandler.bestScore(this);
                    }
                }
                case BACK -> {
                    if (!(isGameStarted() && !isFirstMove())) {
                        messageHandler.invalidCommand();
                        continue;
                    }
                    goPreviousTurn(field);
                }
                case FALSE -> messageHandler.invalidCommand();
                case MOVE, EXIT -> {
                }
            }
        }
    }

    private boolean initializeGame() {
        if (isGameStarted()) {
            observer = new Observer(field, getTurn());

            if (observer.isEndOfGame()) {
                if (getBestScore() < observer.getScore().getFirst()) {
                    setBestScore(observer.getScore().getFirst());
                }
                if (getGameMode() == 1 && getBestScore() < observer.getScore().getSecond()) {
                    setBestScore(observer.getScore().getSecond());
                }

                messageHandler.gameEnd(observer, this);
                messageHandler.showField(field);

                setGameNotStarted();

                return true;
            }

            if (observer.getPossibleMoves().isEmpty()) {
                messageHandler.skipTurn(getTurn());
                setTurn(!turn);

                return true;
            }
        }

        return false;
    }

    private boolean initializeVariables() {
        gameMode = commandGrabber.getGameMode();
        switch (gameMode) {
            case 1 -> {
                blackPlayer = new Human(commandGrabber, messageHandler);
                whitePlayer = new Human(commandGrabber, messageHandler);
            }
            case 2, 3 -> {
                blackPlayer = new Human(commandGrabber, messageHandler);
                whitePlayer = new Computer(getGameMode(), commandGrabber, messageHandler, this);
            }
            case 0 -> {
                return false;
            }
        }

        field = new Field();
        observer = new Observer(field, getTurn());
        fields = new ArrayDeque<>();
        setTurn(true);

        return true;
    }

    public boolean isFirstMove() {
        return fields.isEmpty();
    }

    public int getGameMode() {
        return gameMode;
    }

    private boolean doMove(Observer observer, Field field, Player blackPlayer, Player whitePlayer) {
        messageHandler.showField(Observer.addMovesToField(observer.getPossibleMoves(), field));
        messageHandler.turnInfo(this);

        fields.push(field.getField());

        Player currentPlayer = getTurn() ? blackPlayer : whitePlayer;
        IntegerPair move = currentPlayer.makeMove(observer);

        if (move.getFirst() == -1 && move.getSecond() == -1) {
            fields.pop();
            return false;
        }

        field.setCell(move, getTurn() ? FiledSymbols.BLACK : FiledSymbols.WHITE);
        observer.changeField(move, field);

        setTurn(!turn);
        return true;
    }

    private void goPreviousTurn(Field field) {
        setTurn(!turn);
        field.setField(fields.pop());

        if (getGameMode() == 2 || getGameMode() == 3) {
            field.setField(fields.pop());
            setTurn(!turn);
        }
    }

    private void info() {
        messageHandler.startExitInfo();
        if (isGameStarted()) {
            messageHandler.commandsInfo();
            if (!isFirstMove()) {
                messageHandler.backInfo();
            }
        }
    }
}
