package ru.hse.reversi.commands;

import ru.hse.reversi.game.Observer;

public class GUICommandGrabber implements CommandGrabber {

    @Override
    public Command getCommand() {
        return null;
    }

    @Override
    public String getMove(Observer observer) {
        return null;
    }

    @Override
    public int getGameMode() {
        return 0;
    }
}
