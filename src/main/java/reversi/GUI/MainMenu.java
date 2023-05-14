package reversi.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    private final JButton exitButton;
    private final JButton pvcEasyButton;
    private final JButton pvcHardButton;
    private final JButton pvpButton;
    private final BoardGUI gui;

    /**
     * Constructs a new MainMenu object that draws a window with the main menu.
     */
    public MainMenu() {
        setTitle("Main menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        pvcEasyButton = new JButton("Компьютер (легкий)");
        pvcEasyButton.addActionListener(this);

        pvcHardButton = new JButton("Компьютер (сложный)");
        pvcHardButton.addActionListener(this);

        pvpButton = new JButton("Игрок против игрока");
        pvpButton.addActionListener(this);

        exitButton = new JButton("Выход");
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

    /**
     * Handles the action event when a button is pressed.
     * @param e the event that occurred
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pvpButton) {
            gui.createGame("Human", gui.getBestScore());
            gui.setMainMenu(this);
            gui.setVisible(true);
            setVisible(false);
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