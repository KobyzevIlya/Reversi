package ru.hse.reversi.players;

import ru.hse.reversi.commands.CommandGrabber;
import ru.hse.reversi.game.Observer;
import ru.hse.reversi.messages.MessageHandler;
import ru.hse.reversi.utility.IntegerPair;

import java.util.ArrayList;
import java.util.List;

public class Human implements Player {
    private final CommandGrabber commandGrabber;
    private final MessageHandler messageHandler;

    public Human(CommandGrabber commandGrabber, MessageHandler messageHandler) {
        this.commandGrabber = commandGrabber;
        this.messageHandler = messageHandler;
    }

    @Override
    public IntegerPair makeMove(Observer observer) {
        ArrayList<IntegerPair> possibleMoves = (ArrayList<IntegerPair>) observer.getPossibleMoves();

        while (true) {
            String move = commandGrabber.getMove(observer);
            switch (checkMove(move, possibleMoves)) {
                case -1 -> {
                    return new IntegerPair(-1, -1);
                }
                case 1 -> {
                    messageHandler.chosenLetter(move.charAt(0));
                    return possibleMoves.get(move.charAt(0) - '0' - 49);
                }
                case 0 -> {
                    System.out.print("->Invalid move. Try again\n");
                }
            }
        }
    }

    private int checkMove(String input, List<IntegerPair> possibleMoves) {
        if (input.length() != 1) return 0;
        char move = input.charAt(0);
        if (move == '0') {
            return -1;
        } else if (move - '0' >= 'a' - '0' && move - '0' < 'a' - '0' + possibleMoves.size()) {
            return 1;
        }
        return 0;
    }
}
