package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game2048 extends JFrame {
    private final GameBoard gameBoard;
    private final JLabel scoreLabel;

    public Game2048() {
        super("Game 2048 - 5x5");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 应用FlatLaf现代UI主题
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(50, 50, 50));

        // 创建标题面板
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("2048");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(238, 228, 218));

        // 创建分数面板
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(new Color(187, 173, 160));
        scorePanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));

        JLabel scoreTitle = new JLabel("SCORE");
        scoreTitle.setFont(new Font("Arial", Font.BOLD, 14));
        scoreTitle.setForeground(new Color(238, 228, 218));
        scoreTitle.setAlignmentX(CENTER_ALIGNMENT);

        scoreLabel = new JLabel("0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 22));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);

        scorePanel.add(scoreTitle);
        scorePanel.add(scoreLabel);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(scorePanel, BorderLayout.EAST);

        // 创建游戏面板
        gameBoard = new GameBoard();

        // 创建控制面板
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        JButton restartButton = new JButton("New Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 14));
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(e -> restartGame());
        controlPanel.add(restartButton);

        // 添加组件
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(gameBoard, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);


        // 添加键盘监听
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean moved = false;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        moved = gameBoard.moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        moved = gameBoard.moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        moved = gameBoard.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        moved = gameBoard.moveRight();
                        break;
                }

                if (moved) {
                    gameBoard.addRandomTile();
                    updateUI();

                    if (gameBoard.isGameOver()) {
                        showGameOverDialog();
                    }
                }
            }
        });
    }

    private void restartGame() {
        gameBoard.resetGame();
        updateUI();
    }

    private void updateUI() {
        gameBoard.updateBoard();
        scoreLabel.setText(String.valueOf(gameBoard.getScore()));
        repaint();
    }

    private void showGameOverDialog() {
        Object[] options = {"New Game", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Game Over! Your score: " + gameBoard.getScore(),
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game2048 game = new Game2048();
            game.setVisible(true);
            game.requestFocusInWindow();
        });
    }
}