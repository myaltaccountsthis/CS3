import java.util.List;

public abstract class MazeSolver {
    private Maze maze;
    private boolean solved;
    private boolean solvable;

    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.solved = false;
        this.solvable = true;
        makeEmpty();
        add(maze.getStart());
    }

    public abstract void makeEmpty();

    public abstract boolean isEmpty();

    public abstract void add(Square s);

    public abstract Square next();

    public boolean isSolved() {
        return solved;
    }

    public void step() {
        if (isEmpty()) {
            solvable = false;
            return;
        }

        Square next = next();

        System.out.println(next.getRow() + ", " + next.getCol());
        if (next.equals(maze.getExit())) {
            solved = true;
            return;
        }
        List<Square> neighbors = maze.getNeighbors(next);
        next.setStatus(Square.EXPLORED);
        for (Square neighbor : neighbors) {
            if (neighbor.getStatus() == '_' && neighbor.getType() != Square.WALL) {
                add(neighbor);
                neighbor.setStatus(Square.WORKING);
            }
        }
    }

    public String getPath() {
        return !solvable ? "Maze is not solvable" : solved ? "Maze is solved" : "Maze is not yet solved";
    }

    public void solve() {
        while (!solved && solvable) {
            step();
        }
    }
}
