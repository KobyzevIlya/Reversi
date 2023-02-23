package ru.hse.reversi.GUI;

import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JFrame {

    public BoardGUI() {
        // create a window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Reversi");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 250, dimension.height / 2 - 350, 600, 600);
        setLayout(new BorderLayout());

        // create a board
        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // create buttons
        for (int row = 0; row < 8; ++row) {
            for (int col = 0; col < 8; ++col) {
                JButton[][] squares = new JButton[8][8];
                squares[row][col] = new JButton();
                squares[row][col].setOpaque(true);
                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(Color.GRAY);
                } else {
                    squares[row][col].setBackground(Color.WHITE);
                }
                board.add(squares[row][col]);
            }
        }

        // create labels A-H
        JPanel fileLabelsPanel = new JPanel(new GridLayout(1, 8));
        for (int col = 0; col < 8; ++col) {
            String[] fileLabels = {"A", "B", "C", "D", "E", "F", "G", "H"};
            JLabel label = new JLabel(fileLabels[col], SwingConstants.CENTER);
            fileLabelsPanel.add(label, BorderLayout.SOUTH);
        }

        // create labels 1-8
        JPanel rankLabelsPanel = new JPanel(new GridLayout(8, 1));
        for (int row = 0; row < 8; ++row) {
            String[] rankLabels = {"1", "2", "3", "4", "5", "6", "7", "8"};
            JLabel label = new JLabel(rankLabels[7-row], SwingConstants.CENTER);
            rankLabelsPanel.add(label);
        }

        // add panels with labels to the main panel
        add(fileLabelsPanel, BorderLayout.NORTH);
        add(rankLabelsPanel, BorderLayout.WEST);
        add(board, BorderLayout.CENTER);
    }
}
