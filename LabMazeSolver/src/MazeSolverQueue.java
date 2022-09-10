import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MazeSolverQueue extends MazeSolver {
    private Queue<Square> queue;
    private Square exit;

    private int distance(Square a, Square b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol());
    }

    private int heuristic(Square a) {
        return a.getDistance() + distance(a, exit);
    }

    public MazeSolverQueue(Maze maze) {
        super(maze);
        exit = maze.getExit();
    }

    @Override
    public void makeEmpty() {
        queue = new PriorityQueue<>(Comparator.comparingInt(this::heuristic));
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void add(Square s) {
        queue.offer(s);
    }

    @Override
    public Square next() {
        return queue.poll();
    }
}
