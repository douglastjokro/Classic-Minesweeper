import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends JFrame implements ActionListener, MouseListener {
    private final int BOARD_SIZE = 10;
    private final int NUM_MINES = 10;
    private JButton[][] buttons;
    private int[][] board;
    private boolean[][] visited;
    private int numUncovered;
    private boolean gameOver;

    public Minesweeper() {
        super("Minesweeper");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        board = new int[BOARD_SIZE][BOARD_SIZE];
        visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        numUncovered = 0;
        gameOver = false;

        // create the buttons and add them to the JFrame
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].addActionListener(this);
                buttons[row][col].addMouseListener(this);
                add(buttons[row][col]);
            }
        }

        // generate the board and set the size of the JFrame
        generateBoard();
        setSize(800, 800);
        setVisible(true);
    }

    private void generateBoard() {
        // initialize board with zeros
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = 0;
            }
        }

        // randomly place mines on the board
        for (int i = 0; i < NUM_MINES; i++) {
            int row = (int) (Math.random() * BOARD_SIZE);
            int col = (int) (Math.random() * BOARD_SIZE);

            // check if there is already a mine in this location
            if (board[row][col] == -1) {
                i--;
            } else {
                board[row][col] = -1;
            }
        }

        // fill in the rest of the board with numbers indicating the number of adjacent
        // mines
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == -1) {
                    // skip if this cell is a mine
                    continue;
                }

                int count = 0;

                // check neighboring cells for mines
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i < 0 || i >= BOARD_SIZE || j < 0 || j >= BOARD_SIZE) {
                            // skip if cell is out of bounds
                            continue;
                        }

                        if (board[i][j] == -1) {
                            count++;
                        }
                    }
                }

                board[row][col] = count;
            }
        }
    }

    private void uncoverCell(int row, int col) {
        if (visited[row][col]) {
            return;
        }

        visited[row][col] = true;
        numUncovered++;
        buttons[row][col].setEnabled(false); // update the button to be disabled

        // display the number of adjacent mines or show all mines if it's a mine
        if (board[row][col] == -1) {
            buttons[row][col].setText("X");
            gameOver = true;
            // show all mines
            for (int r = 0; r < BOARD_SIZE; r++) {
                for (int c = 0; c < BOARD_SIZE; c++) {
                    if (board[r][c] == -1) {
                        buttons[r][c].setText("X");
                        buttons[r][c].setEnabled(false);
                    }
                }
            }
            // create a new dialog with a restart button
            JLabel messageLabel = new JLabel("Game over!");
            messageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            int choice = JOptionPane.showOptionDialog(this, "Game over!", "Game over", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, new Object[] { "Restart" }, null);

            // restart the game if the restart button is clicked
            if (choice == 0) {
                resetGame();
            }
        } else if (board[row][col] == 0) {
            buttons[row][col].setText("");
            // recursively uncover neighboring cells
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (i < 0 || i >= BOARD_SIZE || j < 0 || j >= BOARD_SIZE) {
                        continue;
                    }
                    if (!visited[i][j]) {
                        uncoverCell(i, j);
                    }
                }
            }
        } else {
            buttons[row][col].setText(String.valueOf(board[row][col]));
        }

        // check if the player has won
        if (numUncovered == BOARD_SIZE * BOARD_SIZE - NUM_MINES) {
            JOptionPane.showMessageDialog(this, "You win!");
        }
    }

    private void resetGame() {
        // reset the board and visited arrays
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = 0;
                visited[row][col] = false;
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }

        // regenerate the board with new mine locations
        generateBoard();

        // reset game variables
        numUncovered = 0;
        gameOver = false;
    }

    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return;
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (e.getSource() == buttons[row][col]) {
                    uncoverCell(row, col);
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if (gameOver) {
            return;
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (e.getSource() == buttons[row][col] && e.getButton() == MouseEvent.BUTTON3) {
                    // right-click to mark a cell as a mine
                    if (buttons[row][col].getText().equals("")) {
                        buttons[row][col].setText("M");
                    } else if (buttons[row][col].getText().equals("M")) {
                        buttons[row][col].setText("");
                    }
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public static void main(String[] args) {
        new Minesweeper();
    }
}
