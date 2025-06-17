package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends JPanel {
    private static final int SIZE = 5;
    static final int TILE_SIZE = 80; //实际使用的方块尺寸
    private static final int SPACING = 8; //方块间距

    private final Title[][] tiles;
    private int[][] board;
    private int score;

    public GameBoard() {
        //设置布局管理器，应用SPACING作为水平和垂直间隙
        setLayout(new GridLayout(SIZE, SIZE, SPACING, SPACING));
        setBackground(new Color(187, 173, 160));
        setBorder(BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING));
        tiles = new Title[SIZE][SIZE];
        board = new int[SIZE][SIZE];
        score = 0;

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                tiles[r][c] = new Title();
                //确保每个title组件应用TITLE_SIZE
                add(tiles[r][c]);
            }
        }

        resetGame();
    }

    public void resetGame() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                board[r][c] = 0;
            }
        }

        score = 0;
        addRandomTile();
        addRandomTile();
        addRandomTile();

        updateBoard();
    }

    void addRandomTile() {
        List<Point> emptyCells = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == 0) {
                    emptyCells.add(new Point(r, c));
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            Random random = new Random();
            Point randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
            board[randomCell.x][randomCell.y] = random.nextInt(100) < 85 ? 2 : 4;
        }
    }

    void updateBoard() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                tiles[r][c].setValue(board[r][c]);
            }
        }
    }

    public boolean moveUp() {
        boolean moved = false;
        for (int c = 0; c < SIZE; c++) {
            for (int r = 1; r < SIZE; r++) {
                if (board[r][c] != 0) {
                    int currentRow = r;
                    while (currentRow > 0 && board[currentRow - 1][c] == 0) {
                        board[currentRow - 1][c] = board[currentRow][c];
                        board[currentRow][c] = 0;
                        currentRow--;
                        moved = true;
                    }

                    if (currentRow > 0 && board[currentRow - 1][c] == board[currentRow][c]) {
                        board[currentRow - 1][c] *= 2;
                        score += board[currentRow - 1][c];
                        board[currentRow][c] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    public boolean moveDown() {
        boolean moved = false;
        for (int c = 0; c < SIZE; c++) {
            for (int r = SIZE - 2; r >= 0; r--) {
                if (board[r][c] != 0) {
                    int currentRow = r;
                    while (currentRow < SIZE - 1 && board[currentRow + 1][c] == 0) {
                        board[currentRow + 1][c] = board[currentRow][c];
                        board[currentRow][c] = 0;
                        currentRow++;
                        moved = true;
                    }

                    if (currentRow < SIZE - 1 && board[currentRow + 1][c] == board[currentRow][c]) {
                        board[currentRow + 1][c] *= 2;
                        score += board[currentRow + 1][c];
                        board[currentRow][c] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    public boolean moveLeft() {
        boolean moved = false;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 1; c < SIZE; c++) {
                if (board[r][c] != 0) {
                    int currentCol = c;
                    while (currentCol > 0 && board[r][currentCol - 1] == 0) {
                        board[r][currentCol - 1] = board[r][currentCol];
                        board[r][currentCol] = 0;
                        currentCol--;
                        moved = true;
                    }

                    if (currentCol > 0 && board[r][currentCol - 1] == board[r][currentCol]) {
                        board[r][currentCol - 1] *= 2;
                        score += board[r][currentCol - 1];
                        board[r][currentCol] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    public boolean moveRight() {
        boolean moved = false;
        for (int r = 0; r < SIZE; r++) {
            for (int c = SIZE - 2; c >= 0; c--) {
                if (board[r][c] != 0) {
                    int currentCol = c;
                    while (currentCol < SIZE - 1 && board[r][currentCol + 1] == 0) {
                        board[r][currentCol + 1] = board[r][currentCol];
                        board[r][currentCol] = 0;
                        currentCol++;
                        moved = true;
                    }

                    if (currentCol < SIZE - 1 && board[r][currentCol + 1] == board[r][currentCol]) {
                        board[r][currentCol + 1] *= 2;
                        score += board[r][currentCol + 1];
                        board[r][currentCol] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    public boolean isGameOver() {
        // check is there still have empty board
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == 0) {
                    return false;
                }
            }
        }
        // check is there still have could plus board
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                int value = board[r][c];

                if (c < SIZE - 1 && board[r][c + 1] == value) {
                    return false;
                }

                if (r < SIZE - 1 && board[r + 1][c] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getScore() {
        return score;
    }

    @Override
    public Dimension getPreferredSize() {
        // 计算总尺寸: (方块尺寸 * 数量) + (间距 * (数量 + 1))
        int width = SIZE * TILE_SIZE + (SIZE + 1) * SPACING;
        int height = SIZE * TILE_SIZE + (SIZE + 1) * SPACING;
        return new Dimension(width, height);
    }
}

