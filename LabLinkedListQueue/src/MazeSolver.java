import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public abstract class MazeSolver {
    private Maze maze;
    private boolean solved;
    private boolean solvable;
    private List<Square> path;

    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.solved = false;
        this.solvable = true;
        makeEmpty();
        if (maze.getStart() != null) {
            add(maze.getStart());
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
        if (solved)
            return;
        if (isEmpty()) {
            solvable = false;
            return;
        }

        Square next = next();

        if (next.equals(maze.getExit())) {
            solved = true;
            Stack<Square> path = new Stack<>();
            this.path = new ArrayList<>();
            path.push(next);
            Square child = next.getParent();

            while (child != null) {
                child.setStatus(Square.ON_PATH);
                path.push(child);
                child = child.getParent();
            }

            while (!path.empty())
                this.path.add(path.pop());

            return;
        }
        List<Square> neighbors = maze.getNeighbors(next);
        next.setStatus(Square.EXPLORED);
        for (Square neighbor : neighbors) {
            if (neighbor.getStatus() == Square.UNEXPLORED && neighbor.getType() != Square.WALL) {
                neighbor.setStatus(Square.WORKING);
                neighbor.setParent(next);
                add(neighbor);
            }
        }
    }

    public String getPath() {
        if (!solvable)
            return "Maze is not solvable";
        if (!solved)
            return "Maze is not yet solved";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            if (i > 0)
                sb.append(", ");
            Square s = path.get(i);
            sb.append('[').append(s.getRow()).append(',').append(s.getCol()).append(']');
        }
        return sb.toString();
    }

    public void solve() {
        while (!solved && solvable) {
            step();
        }
    }
}
