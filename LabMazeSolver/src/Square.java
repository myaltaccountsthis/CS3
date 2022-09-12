public class Square {
    public static final int EMPTY = 0, WALL = 1, START = 2, EXIT = 3;

    public static final char UNEXPLORED = '_', WORKING = 'o', EXPLORED = '.', ON_PATH = 'x';

    private int row, col;
    private int type;
    private char status;
    private int distance;
    private Square parent;

    public Square(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.distance = 1000000;
        this.parent = null;
    }

    @Override
    public String toString() {
        switch(type) {
            case WALL:
                return "#";
            case START:
                return "S";
            case EXIT:
                return "E";
            default:
                return status + "";
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return row == square.row && col == square.col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getType() {
        return type;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public void reset() {
        this.status = UNEXPLORED;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Square getParent() {
        return parent;
    }

    public void setParent(Square parent) {
        this.parent = parent;
    }
}
