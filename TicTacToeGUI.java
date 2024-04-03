import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI {
    private static final int BOARD_SIZE = 3;
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";
    private static final String EMPTY = "";

    private JFrame frame;
    private JButton[][] buttons;
    private String currentPlayer;
    private JLabel statusLabel;

    public TicTacToeGUI() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 50));
                final int r = row, c = col;
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttonClicked(r, c);
                    }
                });
                boardPanel.add(buttons[row][col]);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel();
        statusLabel = new JLabel("Player X's turn");
        statusPanel.add(statusLabel);
        frame.add(statusPanel, BorderLayout.SOUTH);

        currentPlayer = PLAYER_X;

        frame.setVisible(true);
    }

    private void buttonClicked(int row, int col) {
        if (buttons[row][col].getText().equals(EMPTY)) {
            buttons[row][col].setText(currentPlayer);
            if (checkWinner(row, col)) {
                JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
                resetBoard();
            } else if (checkDraw()) {
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                resetBoard();
            } else {
                currentPlayer = currentPlayer.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;
                statusLabel.setText("Player " + currentPlayer + "'s turn");
            }
        }
    }

    private boolean checkWinner(int row, int col) {
        String symbol = buttons[row][col].getText();

        // Check row
        boolean win = true;
        for (int c = 0; c < BOARD_SIZE; c++) {
            if (!buttons[row][c].getText().equals(symbol)) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // Check column
        win = true;
        for (int r = 0; r < BOARD_SIZE; r++) {
            if (!buttons[r][col].getText().equals(symbol)) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // Check diagonals
        if (row == col) {
            win = true;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (!buttons[i][i].getText().equals(symbol)) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        if (row + col == BOARD_SIZE - 1) {
            win = true;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (!buttons[i][BOARD_SIZE - 1 - i].getText().equals(symbol)) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (buttons[row][col].getText().equals(EMPTY)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col].setText(EMPTY);
            }
        }
        currentPlayer = PLAYER_X;
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToeGUI();
            }
        });
    }
}
