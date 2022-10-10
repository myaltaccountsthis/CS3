import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

class Board {
    private boolean[] usedRows;
    private boolean[] usedCols;
    private boolean[][] grid;
    private int size;

    public Board(int size) {
        this.usedRows = new boolean[size];
        this.usedCols = new boolean[size];
        this.grid = new boolean[size][size];
        this.size = size;
    }

    public boolean safe(int row, int col) {
        if (usedRows[row] || usedCols[col])
            return false;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (grid[r][c] && Math.abs(r - row) == Math.abs(c - col))
                    return false;
            }
        }

        return true;
    }

    public void place(int row, int col) {
        usedRows[row] = true;
        usedCols[col] = true;
        grid[row][col] = true;
    }

    public void remove(int row, int col) {
        usedRows[row] = false;
        usedCols[col] = false;
        grid[row][col] = false;
    }

    public boolean isSolved() {
        for (boolean b : usedRows) {
            if (!b)
                return false;
        }
        for (boolean b : usedCols) {
            if (!b)
                return false;
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (boolean[] row : grid) {
            for (boolean b : row) {
                sb.append(b ? 'X' : '_').append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

class BoardFrame extends Board
{
    private static final ImageIcon QUEEN;
    private static final ImageIcon QUESTION_MARK;
    private static final ImageIcon ERROR;

    static {
        QUEEN = new ImageIcon("queen.png");
        QUESTION_MARK = new ImageIcon("questionmark.png");
        ERROR = new ImageIcon("error.png");
    }

    private static Icon getScaledIcon(ImageIcon original) {
        return new ImageIcon(original.getImage().getScaledInstance(63, 63, Image.SCALE_DEFAULT));
    }

    JButton[][] myButtons;
    int         delay;

    public BoardFrame(int size)
    {
        super(size);

        JFrame f = new JFrame();

        f.setSize(60 * size + 50, 60 * size + 80);
        f.setResizable(false);
        f.setTitle("Eight Queens Animation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = f.getContentPane();

        // initialize delay and add slider to control it at bottom
        final int maxPause = 1000; // milliseconds
        final JSlider slider = new JSlider(0, maxPause - 20);
        delay = slider.getValue();
        slider.addChangeListener(e -> delay = maxPause - slider.getValue());
        JPanel p = new JPanel();
        p.add(new JLabel("slow"));
        p.add(slider);
        p.add(new JLabel("fast"));
        contentPane.add(p, "South");

        // add buttons in the middle for the chess squares
        p = new JPanel(new GridLayout(size, size, 1, 1));
        contentPane.add(p, "Center");
        p.setBackground(Color.black);
        myButtons = new JButton[size][size];
        Font f24 = new Font("Serif", Font.BOLD, 24);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                JButton b = new JButton();
                b.setFont(f24);
                p.add(b);
                myButtons[i][j] = b;
            }

        f.setVisible(true);
        f.toFront();
    }

    public void place(int row, int col) {
        super.place(row, col);
        myButtons[row][col].setIcon(getScaledIcon(QUEEN));
        //myButtons[row][col].setText("Q");
        pause();
    }

    public boolean safe(int row, int col) {
        myButtons[row][col].setIcon(getScaledIcon(QUESTION_MARK));
        //myButtons[row][col].setText("?");
        pause();
        myButtons[row][col].setIcon(new ImageIcon());
        //myButtons[row][col].setText("");
        return super.safe(row, col);
    }

    public void remove(int row, int col) {
        super.remove(row, col);
        myButtons[row][col].setIcon(getScaledIcon(ERROR));
        //myButtons[row][col].setText("No");
        pause();
        myButtons[row][col].setIcon(new ImageIcon());
        //myButtons[row][col].setText("");
    }

    private void pause() {
        try {
            Thread.sleep(delay);
        }
        catch (InterruptedException e) {}
    }
}

class KnightsTour {
    // moves are listed in clockwise order from 12 o'clock
    private static final int[] xMoves = new int[] {1, 2, 2, 1, -1, -2, -2, -1};
    private static final int[] yMoves = new int[] {2, 1, -1, -2, -2, -1, 1, 2};
    private int[][] board;
    private int size;

    public KnightsTour(int size) {
        this.board = new int[size][size];
        this.size = size;
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
    public boolean isValid(int row, int col) {
        return inBounds(row, col) && board[row][col] == 0;
    }

    public void solve(int row, int col) {
        boolean success = recursiveSolve(row, col, 1);
        System.out.println(success ? "Solved:\n" + this : "Did not solve");
    }
    private boolean recursiveSolve(int row, int col, int n) {
        board[row][col] = n;
        if (n == size * size) {
            return true;
        }

        for (int i = 0; i < xMoves.length; i++) {
            int r = row + yMoves[i];
            int c = col + xMoves[i];
            if (isValid(r, c)) {
                boolean success = recursiveSolve(r, c, n + 1);
                if (success)
                    return true;
            }
        }

        board[row][col] = 0;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int n : row)
                sb.append(n).append('\t');
            sb.append('\n');
        }
        return sb.toString();
    }
}

public class ForestFireAdvanced {
    public void eightQueens() {
        BoardFrame board = new BoardFrame(8);

        // repeat random starting position until solution is found
        while (true) {
            board.place((int) (Math.random() * board.getSize()), 0);
            eightQueensHelper(board, 1);
            if (board.isSolved()) {
                System.out.println(board);
                return;
            }
        }
    }
    private boolean eightQueensHelper(Board board, int col) {
        if (col >= board.getSize()) {
            return board.isSolved();
        }

        for (int row = 0; row < board.getSize(); row++) {
            if (board.safe(row, col)) {
                board.place(row, col);
                boolean success = eightQueensHelper(board, col + 1);
                if (success)
                    return true;
                board.remove(row, col);
            }
        }

        return false;
    }

    public void knightsTour(int size, int row, int col) {
        KnightsTour runner = new KnightsTour(size);
        runner.solve(row, col);
    }

    public static void main(String[] args) {
        ForestFireAdvanced advanced = new ForestFireAdvanced();

        //advanced.eightQueens();

        advanced.knightsTour(8, 0, 0);
        advanced.knightsTour(5, 0, 2);
    }
}
