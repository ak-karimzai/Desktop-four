package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class Board implements ActionListener {
    private JPanel frame;
    private JButton[][] matrix;
    private boolean playerWon = false;
    private Color wonColor = Color.GREEN;
    private int rowLength = 6;
    private int colLength = 7;

    private static AtomicBoolean flag = new AtomicBoolean(false);

    public Board(JPanel frame) {
        this.frame = frame;
        initBoard();
    }

    private void initBoard() {
        matrix = new JButton[rowLength][colLength];
        for (int i = 0; i < matrix.length; i++) {
            int word = 65;
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new JButton();
                String cellName = String.valueOf((char) (word + j)) + (6 - i);
                matrix[i][j].setName("Button" + cellName);
                matrix[i][j].setText(" ");
                matrix[i][j].addActionListener(this);
                matrix[i][j].setBackground(this.frame.getBackground());
                this.frame.add(matrix[i][j]);
            }
        }
    }

    private Pair<Integer, Integer> buttonIndexes(JButton src) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == src) {
                    return new Pair<Integer, Integer>(i, j);
                }
            }
        }
        return new Pair<Integer, Integer>(-1, -1);
    }

    private int buttonRow(JButton src) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == src) {
                    return i;
                }
            }
        }
        return -1;
    }


    private JButton getFreeColumn(JButton src) {
        var colNumber = buttonIndexes(src).getSecond();
        for (int i = matrix.length - 1; i >= 0; i--) {
            var buttonText = matrix[i][colNumber].getText();
            if (!(buttonText.equals("O") || buttonText.equals("X"))) {
                return matrix[i][colNumber];
            }
        }
        return src;
    }

    public void resetBoard() {
        playerWon = false;
        flag.set(false);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j].setText(" ");
                matrix[i][j].setBackground(this.frame.getBackground());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = getFreeColumn((JButton) e.getSource());

        if (isPlayerWon()) {
            return;
        }

        var buttonText = source.getText();
        if (!(buttonText.equals("X") || buttonText.equals("O"))) {
            if (!flag.get()) {
                source.setText("X");
            } else {
                source.setText("O");
            }
            flag.set(!flag.get());
        }
        isWin(source);
    }

    public boolean isPlayerWon() {
        return playerWon;
    }

    public boolean isWin(JButton button) {
        if (playerWon) return playerWon;

        if (checkVertically(button) || checkHorizantally(button) || checkDiagonally(button)) {
            playerWon = true;
        }
        return playerWon;
    }

    private void changeColorVer(int row, int col) {
        var colText = matrix[row][col].getText();

        for (int i = col; i < matrix[0].length; i++) {
            var buttonText = matrix[row][i].getText();
            if (buttonText.equals(colText)) {
                matrix[row][i].setBackground(wonColor);
            } else {
                break;
            }
        }

        for (int i = col - 1; i >= 0; i--) {
            var buttonText = matrix[row][i].getText();
            if (buttonText.equals(colText)) {
                matrix[row][i].setBackground(wonColor);
            } else {
                break;
            }
        }
    }

    private void changeColorHor(int row, int col) {
        int rowLength = matrix.length;
        var colText = matrix[row][col].getText();

        for (int i = row; i < rowLength; i++) {
            var buttonText = matrix[i][col].getText();
            if (buttonText.equals(colText)) {
                matrix[i][col].setBackground(wonColor);
            } else {
                break;
            }
        }

        for (int i = row - 1; i >= 0; i--) {
            var buttonText = matrix[i][col].getText();
            if (buttonText.equals(colText)) {
                matrix[i][col].setBackground(wonColor);
            } else {
                break;
            }
        }
    }

    private boolean checkVertically(JButton button) {
        var colText = button.getText();
        var indexes = buttonIndexes(button);
        int buttonRow = indexes.getFirst();
        int buttonCol = indexes.getSecond();

        int count = 0;
        for (int i = buttonCol; i < colLength; i++) {
            var buttonText = matrix[buttonRow][i].getText();
            if (buttonText.equals(colText)) {
                count++;
            } else {
                break;
            }
        }

        for (int i = buttonCol - 1; i >= 0; i--) {
            var buttonText = matrix[buttonRow][i].getText();
            if (buttonText.equals(colText)) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            changeColorVer(buttonRow, buttonCol);
            return true;
        }
        return false;
    }

    private boolean checkHorizantally(JButton button) {
        var colText = button.getText();
        var indexes = buttonIndexes(button);
        int buttonRow = indexes.getFirst();
        int buttonCol = indexes.getSecond();

        int count = 0;
        for (int i = buttonRow; i < rowLength; i++) {
            var buttonText = matrix[i][buttonCol].getText();
            if (buttonText.equals(colText)) {
                count++;
            } else {
                break;
            }
        }

        for (int i = buttonRow - 1; i >= 0; i--) {
            var buttonText = matrix[i][buttonCol].getText();
            if (buttonText.equals(colText)) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            changeColorHor(buttonRow, buttonCol);
        }
        return count >= 4 ? true : false;
    }

    private void changeColorDiag(int row, int col, boolean flag) {
        var colText = matrix[row][col].getText();

        if (flag) {
            for (int i = row, j = col; i < rowLength && j >= 0; i++, j--) {
                var buttonText = matrix[i][j].getText();
                if (buttonText.equals(colText)) {
                    matrix[i][j].setBackground(wonColor);
                } else {
                    break;
                }
            }

            for (int i = row - 1, j = col + 1; i >= 0 && j < colLength; i--, j++) {
                var buttonText = matrix[i][j].getText();
                if (buttonText.equals(colText)) {
                    matrix[i][j].setBackground(wonColor);
                } else {
                    break;
                }
            }
        } else {
            for (int i = row, j = col; i < rowLength && j < colLength; i++, j++) {
                var buttonText = matrix[i][j].getText();
                if (buttonText.equals(colText)) {
                    matrix[i][j].setBackground(wonColor);
                } else {
                    break;
                }
            }

            for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
                var buttonText = matrix[i][j].getText();
                if (buttonText.equals(colText)) {
                    matrix[i][j].setBackground(wonColor);
                } else {
                    break;
                }
            }
        }
    }
    private boolean checkDiagonally(JButton button) {
        var colText = button.getText();
        var indexes = buttonIndexes(button);
        int buttonRow = indexes.getFirst();
        int buttonCol = indexes.getSecond();
        boolean res = false;

        int count = 0;
        for (int i = buttonRow, j = buttonCol; i < rowLength && j >= 0; i++, j--) {
            var buttonText = matrix[i][j].getText();
            if (buttonText.equals(colText)) {
                count++;
            } else {
                break;
            }
        }

        for (int i = buttonRow - 1, j = buttonCol + 1; i >= 0 && j < colLength; i--, j++) {
            var buttonText = matrix[i][j].getText();
            if (buttonText.equals(colText)) {
                count++;
            } else {
                break;
            }
        }
        if (count == 4) {
            changeColorDiag(buttonRow, buttonCol, true);
            return true;
        }

        count = 0;
        for (int i = buttonRow, j = buttonCol; i < rowLength && j < colLength; i++, j++) {
            var buttonText = matrix[i][j].getText();
            if (buttonText.equals(colText)) {
                count++;
            } else {
                break;
            }
        }

        for (int i = buttonRow - 1, j = buttonCol - 1; i >= 0 && j >= 0; i--, j--) {
            var buttonText = matrix[i][j].getText();
            if (buttonText.equals(colText)) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            changeColorDiag(buttonRow, buttonCol, false);
            return true;
        }
        return false;
    }
}
