package ru.hse.reversi.GUI;

import ru.hse.reversi.commands.ConsoleCommandGrabber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    private final JButton exitButton;
    private final JButton pvcEasyButton;
    private final JButton pvcHardButton;
    private final JButton pvpButton;
    public static String action = "";
    private BoardGUI gui;

    // draws a window with main menu
    public MainMenu() {
        setTitle("Main menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        pvcEasyButton = new JButton("PVC (easy)");
        pvcEasyButton.addActionListener(this);

        pvcHardButton = new JButton("PVC (hard)");
        pvcHardButton.addActionListener(this);

        pvpButton = new JButton("PVP");
        pvpButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(pvcEasyButton);
        panel.add(pvcHardButton);
        panel.add(pvpButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);

        gui = new BoardGUI();
        gui.setVisible(false);
    }

    // works when we press the button
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pvpButton) {
            gui.createGame("Human", gui.getBestScore());
            gui.setMainMenu(this);
            gui.setVisible(true);
            // dispose();
            setVisible(false);
            /*ConsoleCommandGrabber commandGrabber = new ConsoleCommandGrabber();
            commandGrabber.getCommand();
            action = "/s";*/
        } else if (e.getSource() == pvcEasyButton) {
            gui.createGame("Easy", gui.getBestScore());
            gui.setMainMenu(this);
            gui.setVisible(true);
            setVisible(false);
        } else if (e.getSource() == pvcHardButton) {
            gui.createGame("Hard", gui.getBestScore());
            gui.setMainMenu(this);
            gui.setVisible(true);
            setVisible(false);
        } else if (e.getSource() == exitButton) {
            System.out.println("Exit button clicked!");
            System.exit(0);
        }
    }
}