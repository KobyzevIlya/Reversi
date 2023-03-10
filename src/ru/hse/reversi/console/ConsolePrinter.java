package ru.hse.reversi.console;

import ru.hse.reversi.field.Field;
import ru.hse.reversi.game.Observer;
import ru.hse.reversi.game.Reversi;
import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

import java.util.ArrayList;

public final class ConsolePrinter {
    private ConsolePrinter() {
    }

    public static void printStartExitInfo() {
        System.out.print("""
                ->/s - start new game
                ->/e - exit
                """);
    }

    public static void printCommandsInfo() {
        System.out.print("""
                ->/m - choose position to move
                ->/p - view score
                """);
    }

    public static void printBackInfo() {
        System.out.print("""
                ->/b - go to previous move
                """);
    }

    public static void printTurnInfo(Reversi game) {
        System.out.print("->Now " + (game.getTurn() ? FiledSymbols.BLACK : FiledSymbols.WHITE) + " turn\n");
    }

    public static void printModeInfo() {
        System.out.print("->Choose game: 1 - Human vs Human, 2 - Human vs Computer (normal), 3 - Human vs Computer (hard), 0 to exit\n");
    }

    public static void printSkipInfo(boolean turn) {
        System.out.print("->No possible moves for " + (turn ? FiledSymbols.BLACK : FiledSymbols.WHITE) + "\n->Move will be skipped\n");
    }

    public static void printInvalidCommand() {
        System.out.print("->Invalid command. Try again\n");
    }

    public static void printField(Field field) {
        System.out.print("\n");
        System.out.print("  a b c d e f g h\n");
        for (int i = 0; i < 8; ++i) {
            System.out.print(i + " ");
            for (int j = 0; j < 8; ++j) {
                System.out.print(field.getField().getElementAt(i, j) + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }


    public static void printPositions(Observer observer) {
        System.out.print("->Enter position. To exit print 0\n");

        char position = 'a';
        ArrayList<IntegerPair> possibleMoves = (ArrayList<IntegerPair>) observer.getPossibleMoves();
        System.out.print("->Positions: ");
        for (int i = 0; i < possibleMoves.size(); ++i) {
            System.out.print(position + " ");
            ++position;
        }
        System.out.print("\n");
    }

    public static void printScore(Observer observer) {
        IntegerPair score = observer.getScore();
        System.out.print("\n->" + FiledSymbols.BLACK + " score: " + score.getFirst() + ", " + FiledSymbols.WHITE + " score: " + score.getSecond() + "\n");
    }

    public static void printBestScore(Reversi game) {
        System.out.print("->Best score of all games: " + game.getBestScore() + "\n");
    }

    public static void printEnd() {
        System.out.print("->This is end of the game\n");
    }

    public static void printChosenLetter(char move) {
        System.out.print("\n->move " + move + " was chosen\n");
    }
}