package ru.hse.reversi;

import ru.hse.reversi.GUI.BoardGUI;
import ru.hse.reversi.game.Reversi;

public class MainClass {
    public static void main(String[] args) {
        BoardGUI gui = new BoardGUI();
        gui.setVisible(true);
        Reversi reversi = new Reversi();
        reversi.startGame();
    }
}
