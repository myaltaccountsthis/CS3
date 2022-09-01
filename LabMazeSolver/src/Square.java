import java.util.Objects;

public class Square {
    public static final int EMPTY = 0, WALL = 1, START = 2, EXIT = 3;

    public static final int WORKING = 1, EXPLORED = 2, ON_PATH = 3;

    private int row, col;
    private int type, status;

    public Square(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
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
                return getStatus() + "";
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
        switch (status) {
            case EMPTY:
                return '_';
            case WORKING:
                return 'o';
            case EXPLORED:
                return '.';
            case ON_PATH:
                return 'x';
        }
        return ' ';
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void reset() {
        this.status = 0;
    }
}
