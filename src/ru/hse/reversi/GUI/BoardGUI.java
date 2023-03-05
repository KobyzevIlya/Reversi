package ru.hse.reversi.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class BoardGUI extends JFrame {
    JButton[][] squares = new JButton[8][8];
    boolean[][] buttonPressed = new boolean[8][8];

    public BoardGUI() {
        // create a window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Reversi");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 250, dimension.height / 2 - 350, 600, 600);
        setLayout(new BorderLayout());
        setResizable(false);

        // create a board
        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // create buttons
        for (int row = 0; row < 8; ++row) {
            for (int col = 0; col < 8; ++col) {
                JButton button = new JButton();
                squares[row][col] = button;
                squares[row][col].addActionListener(buttonListener);
                button.putClientProperty("x", row);
                button.putClientProperty("y", col);
                buttonPressed[row][col] = false;
                squares[row][col].setOpaque(true);
                squares[row][col].setBackground(Color.GREEN.darker());
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
            JLabel label = new JLabel(rankLabels[7 - row], SwingConstants.CENTER);
            rankLabelsPanel.add(label);
        }

        // add panels with labels to the main panel
        add(fileLabelsPanel, BorderLayout.NORTH);
        add(rankLabelsPanel, BorderLayout.WEST);
        add(board, BorderLayout.CENTER);
    }

    int k = 0;
    ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource(); // get link to the pushed button
            // get coordinates of the button
            int x = (int) button.getClientProperty("x");
            int y = (int) button.getClientProperty("y");
            if (!buttonPressed[x][y]) {
                if (k == 0) {
                    URL url = getClass().getResource("/resources/black.png");
                    assert url != null;
                    ImageIcon icon = new ImageIcon(url);
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(img));
                    k = 1;
                } else {
                    URL url = getClass().getResource("/resources/white.png");
                    assert url != null;
                    ImageIcon icon = new ImageIcon(url);
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(img));
                    k = 0;
                }
                System.out.println("Button clicked!");
                buttonPressed[x][y] = true;
            }
        }
    };
}
