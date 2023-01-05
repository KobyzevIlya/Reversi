package ru.hse.reversi.players;

import ru.hse.reversi.game.Observer;
import ru.hse.reversi.utility.IntegerPair;

public interface Player {
    IntegerPair makeMove(Observer observer);
}
