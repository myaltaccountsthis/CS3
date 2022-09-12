import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public abstract class MazeSolver {
    private Maze maze;
    private boolean solved;
    private boolean solvable;

    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.solved = false;
        this.solvable = true;
        makeEmpty();
        if (maze.getStart() != null) {
            add(maze.getStart());
            maze.getStart().setDistance(0);
        }
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

        if (next.equals(maze.getExit())) {
            solved = true;
            Square child = next.getParent();
            while (child != null) {
                child.setStatus(Square.ON_PATH);
                child = child.getParent();
            }

            return;
        }
        List<Square> neighbors = maze.getNeighbors(next);
        next.setStatus(Square.EXPLORED);
        for (Square neighbor : neighbors) {
            if (neighbor.getStatus() == Square.UNEXPLORED && neighbor.getType() != Square.WALL) {
                neighbor.setDistance(next.getDistance() + 1);
                neighbor.setStatus(Square.WORKING);
                neighbor.setParent(next);
                add(neighbor);
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
