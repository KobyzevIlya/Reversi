package ru.hse.reversi.GUI;

import ru.hse.reversi.commands.ConsoleCommandGrabber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    private final JButton startButton;
    private final JButton exitButton;
    public static String action = "";
    private BoardGUI gui;

    // draws a window with main menu
    public MainMenu() {
        setTitle("Main menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        startButton = new JButton("Start");
        startButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(startButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);

        gui = new BoardGUI();
        gui.setVisible(false);
    }

    // works when we press the button
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            gui.setVisible(true);
            // dispose();
            setVisible(false);
            /*ConsoleCommandGrabber commandGrabber = new ConsoleCommandGrabber();
            commandGrabber.getCommand();
            action = "/s";*/
        } else if (e.getSource() == exitButton) {
            System.out.println("Exit button clicked!");
            System.exit(0);
        }
    }
}