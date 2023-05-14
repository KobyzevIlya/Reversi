package reversi.GUI;

import javax.swing.*;

import reversi.field.Field;
import reversi.game.Reversi;
import reversi.utility.FiledSymbols;
import reversi.utility.IntegerPair;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class BoardGUI extends JFrame {
    JButton[][] squares = new JButton[8][8];

    private final JButton quitButton = new JButton("Завершить игру");
    private final JLabel turnInfo;

    private Reversi reversi;
    private Field field;
    private MainMenu mainMenu;

    /**
     * Constructs a new BoardGUI with the default settings.
     */
    public BoardGUI() {
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

        // create labels A-H
        JPanel fileLabelsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 44, 10));
        String[] fileLabels = { "A", "B", "C", "D", "E", "F", "G", "H" };
        for (int col = 0; col < 8; ++col) {
            JLabel label = new JLabel(fileLabels[col], SwingConstants.CENTER);
            fileLabelsPanel.add(label);
        }


        // create labels 1-8
        JPanel rankLabelsPanel = new JPanel(new GridLayout(8, 1));
        for (int row = 0; row < 8; ++row) {
            String[] rankLabels = { "1", "2", "3", "4", "5", "6", "7", "8" };
            JLabel label = new JLabel(rankLabels[7 - row], SwingConstants.CENTER);
            rankLabelsPanel.add(label);
        }

        // create the undo, stats, and quit buttons
        JButton undoButton = new JButton("Отменить ход");
        undoButton.addActionListener(new ActionListener() {
            /**
             * The actions performed when the Undo button is pressed
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!reversi.isFieldsHistoryEmpty()) {
                    reversi.setField(reversi.popLastFieldFromHistory());
                    reversi.setTurn(!reversi.getTurn());

                    if (Objects.equals(reversi.getGameMode(), "Easy") || Objects.equals(reversi.getGameMode(), "Hard")) {
                        reversi.setField(reversi.popLastFieldFromHistory());
                        reversi.setTurn(!reversi.getTurn());
                    }

                    reversi.newObserver();
                    field = reversi.getField();
                    disableAndSetButtons();
                    changeTurnInfo();
                    setPossibleMoves();
                }
            }
        });

        JButton statsButton = new JButton("Статистика");
        statsButton.addActionListener(new ActionListener() {
            /**
             * The actions performed when the statsButton button is pressed
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                IntegerPair score = reversi.getObserver().getScore();
                JOptionPane.showMessageDialog(null, "Черные: " + score.getFirst() + " Белые: " + score.getSecond());
            }
        });

        quitButton.addActionListener(new ActionListener() {
            /**
             * The actions performed when the quitButton button is pressed
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                IntegerPair score = reversi.getObserver().getScore();
                reversi.setBestScore(Math.max(Math.max(score.getFirst(), score.getSecond()), reversi.getBestScore()));
                JOptionPane.showMessageDialog(null, "Игра окончена.\nИтоговый счет: " + 
                    "Черные: " + score.getFirst() + " Белые: " + score.getSecond() + "\n"+
                    "Лучший счет: " + reversi.getBestScore());

                    setVisible(false);
                    mainMenu.setVisible(true);
            }
        });

        // create a panel for turn information
        JPanel actionsPanel = new JPanel(new GridLayout(3, 1));
        actionsPanel.add(undoButton);
        actionsPanel.add(statsButton);
        actionsPanel.add(quitButton);

        turnInfo = new JLabel();
        turnInfo.setHorizontalAlignment(JLabel.CENTER);

        // add all components to the frame
        add(fileLabelsPanel, BorderLayout.SOUTH);
        add(rankLabelsPanel, BorderLayout.WEST);
        add(board, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.EAST);
        add(turnInfo, BorderLayout.NORTH);

        setVisible(true);
    }

    ActionListener buttonListener = new ActionListener() {
        /**
         * Called when the button is clicked.
         * Updates the game field with the new move, disables buttons, changes turn info, and makes computer move if necessary.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            reversi.addFieldToHistory(new Field(reversi.getField()));

            JButton button = (JButton) e.getSource(); // get link to the pushed button
            int x = (int) button.getClientProperty("x");
            int y = (int) button.getClientProperty("y");

            IntegerPair move = new IntegerPair(x, y);
            reversi.makeMove(move);
            field = reversi.getField();

            disableAndSetButtons();
            changeTurnInfo();

            if (reversi.isCurrentComputer() || reversi.getObserver().getPossibleMoves().isEmpty()) {
                reversi.makeComputerMove();
                reversi.addFieldToHistory(new Field(reversi.getField()));
                field = reversi.getField();
                disableAndSetButtons();
                changeTurnInfo();


            }
            setPossibleMoves();

            if (reversi.getObserver().isEndOfGame() || reversi.getObserver().getPossibleMoves().isEmpty()) {
                quitButton.doClick();
            }
        }
    };

    /**
     * Creates a new game with the specified game mode and best score.
     * @param gameMode String representing the game mode to be played (either "PVP" or "PVC").
     * @param bestScore int representing the best score achieved in this game mode.
     */
    public void createGame(String gameMode, int bestScore) {
        reversi = new Reversi(gameMode, bestScore);
        field = reversi.getField();

        changeTurnInfo();
        disableAndSetButtons();
        setPossibleMoves();
    }

    /**main
     * Sets the main menu for this Reversi game.
     * @param mainMenu MainMenu object representing the main menu.
     */
    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * Disables all buttons and sets their icons based on the current field.
     */
    private void disableAndSetButtons() {
        for (int row = 0; row < 8; ++row) {
            for (int col = 0; col < 8; ++col) {
                squares[row][col].setEnabled(false);

                if (field.getField().getElementAt(row, col) == FiledSymbols.BLACK) {
                    URL url = getClass().getResource("/black.png");
                    assert url != null;
                    ImageIcon icon = new ImageIcon(url);
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    squares[row][col].setIcon(new ImageIcon(img));
                    squares[row][col].setDisabledIcon(squares[row][col].getIcon());
                } else if (field.getField().getElementAt(row, col) == FiledSymbols.WHITE) {
                    URL url = getClass().getResource("/white.png");
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

    /**
     * Sets the possible moves on the game board by enabling buttons and setting the icon for the move.
     */
    private void setPossibleMoves() {
        for (var mv : reversi.getObserver().getPossibleMoves()) {
            squares[mv.getFirst()][mv.getSecond()].setEnabled(true);
            URL url = getClass().getResource("/red.png");
            assert url != null;
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            squares[mv.getFirst()][mv.getSecond()].setIcon(new ImageIcon(img));
        }
    }

    /**
     * Returns the best score of the current game.
     * @return The best score of the current game, or 0 if the game has not been created.
     */
    public int getBestScore() {
        if (reversi != null) {
            return reversi.getBestScore();
        } 
        return 0;
    }

    /**
     * Changes the turn information displayed on the GUI according to the current player turn.
     */
    private void changeTurnInfo() {
        String text = reversi.getTurn() ? "Сейчас ход черных" : "Сейчас ход белых";
        turnInfo.setText(text);
    }
}
