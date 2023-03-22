package ru.hse.reversi.GUI;

import javax.swing.*;

import ru.hse.reversi.console.ConsolePrinter;
import ru.hse.reversi.field.Field;
import ru.hse.reversi.game.newReversi;
import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class BoardGUI extends JFrame {
    JButton[][] squares = new JButton[8][8];
    boolean[][] buttonPressed = new boolean[8][8];

    newReversi reversi = new newReversi();
    Field field = reversi.getField();

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

                button.putClientProperty("x", row);
                button.putClientProperty("y", col);
                button.setEnabled(false);

                squares[row][col] = button;
                squares[row][col].setOpaque(true);
                squares[row][col].setBackground(Color.GREEN.darker());
                squares[row][col].addActionListener(buttonListener);

                board.add(squares[row][col]);
            }
        }

        disableAndSetButtons();
        setPossibleMoves();

        // create labels A-H
        JPanel fileLabelsPanel = new JPanel(new GridLayout(1, 8));
        for (int col = 0; col < 8; ++col) {
            String[] fileLabels = { "A", "B", "C", "D", "E", "F", "G", "H" };
            JLabel label = new JLabel(fileLabels[col], SwingConstants.CENTER);
            fileLabelsPanel.add(label, BorderLayout.SOUTH);
        }

        // create labels 1-8
        JPanel rankLabelsPanel = new JPanel(new GridLayout(8, 1));
        for (int row = 0; row < 8; ++row) {
            String[] rankLabels = { "1", "2", "3", "4", "5", "6", "7", "8" };
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

            IntegerPair move = new IntegerPair(x, y);
            reversi.makeMove(move);
            field = reversi.getField();

            ConsolePrinter.printField(field); // DEBUG

            disableAndSetButtons();

            if (reversi.isCurrentComputer() || reversi.getObserver().getPossibleMoves().isEmpty()) {
                reversi.makeComputerMove();
                field = reversi.getField();
                disableAndSetButtons();
            }
            ConsolePrinter.printField(field); // DEBUG
            setPossibleMoves();

            if (reversi.getObserver().isEndOfGame()) {
                System.out.println("Thats all");
                System.exit(0);
            }
        }
    };

    private void disableAndSetButtons() {
        for (int row = 0; row < 8; ++row) {
            for (int col = 0; col < 8; ++col) {
                squares[row][col].setEnabled(false);

                if (field.getField().getElementAt(row, col) == FiledSymbols.BLACK) {
                    URL url = getClass().getResource("/resources/black.png");
                    assert url != null;
                    ImageIcon icon = new ImageIcon(url);
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    squares[row][col].setIcon(new ImageIcon(img));
                    squares[row][col].setDisabledIcon(squares[row][col].getIcon());
                } else if (field.getField().getElementAt(row, col) == FiledSymbols.WHITE) {
                    URL url = getClass().getResource("/resources/white.png");
                    assert url != null;
                    ImageIcon icon = new ImageIcon(url);
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    squares[row][col].setIcon(new ImageIcon(img));
                    squares[row][col].setDisabledIcon(squares[row][col].getIcon());
                } else {
                    squares[row][col].setIcon(null);
                    squares[row][col].setDisabledIcon(null);
                }
            }
        }
    }

    private void setPossibleMoves() {
        for (var mv : reversi.getObserver().getPossibleMoves()) {
            squares[mv.getFirst()][mv.getSecond()].setEnabled(true);
        }
    }
}
