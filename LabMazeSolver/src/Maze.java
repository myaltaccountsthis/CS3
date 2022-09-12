import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {
    private Square[][] grid;
    private Square start;
    private Square exit;

    public Maze() {}

    public boolean loadMaze(String fileName) {
        try {
            Scanner input = new Scanner(new File(fileName));
            grid = new Square[input.nextInt()][input.nextInt()];
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[r].length; c++) {
                    int type = input.nextInt();
                    grid[r][c] = new Square(r, c, type);
                    grid[r][c].setStatus(Square.UNEXPLORED);
                    if (type == Square.START)
                        start = grid[r][c];
                    if (type == Square.EXIT)
                        exit = grid[r][c];
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public List<Square> getNeighbors(Square s) {
        List<Square> list = new ArrayList<>();
        int row = s.getRow(), col = s.getCol();
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (Math.abs(r- row + c - col) != 1 || r < 0 || r >= grid.length || c < 0 || c >= grid[0].length)
                    continue;
                list.add(grid[r][c]);
            }
        }
        return list;
    }

    public Square getStart() {
        return start;
    }

    public Square getExit() {
        return exit;
    }

    public void reset() {
        for (Square[] row : grid)
            for (Square s : row)
                s.reset();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Square[] row : grid) {
            for (Square s : row) {
                sb.append(s).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
