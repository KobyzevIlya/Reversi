package reversi.players;

import reversi.game.Observer;
import reversi.utility.IntegerPair;

/**
 * 
 * An interface representing a player in the Reversi game.
 */
public interface Player {
    /**
     * Makes a move in the game based on the current state of the game as observed
     * by the given observer.
     * 
     * @param observer the observer object used to observe the current state of the game.
     * @return an IntegerPair representing the move to be made by the player.
     */
    IntegerPair makeMove(Observer observer);
}
