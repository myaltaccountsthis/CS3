public class MazeSolverStack extends MazeSolver {
    private MyStack stack;

    public MazeSolverStack(Maze maze) {
        super(maze);
    }

    @Override
    public void makeEmpty() {
        this.stack = new MyStack();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void add(Square s) {
        stack.push(s);
    }

    @Override
    public Square next() {
        return stack.pop();
    }
}
