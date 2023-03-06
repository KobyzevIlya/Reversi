package ru.hse.reversi;

import ru.hse.reversi.GUI.BoardGUI;
import ru.hse.reversi.GUI.MainMenu;
import ru.hse.reversi.game.Reversi;

public class MainClass {
    public static void main(String[] args) {
        // MainMenu menu = new MainMenu();
        // menu.setVisible(true);
        Reversi reversi = new Reversi();
        reversi.startGame();
    }
}
