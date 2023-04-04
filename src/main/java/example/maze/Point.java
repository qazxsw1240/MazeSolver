package example.maze;

public class Point {
    public final int row;
    public final int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.row, this.col);
    }
}
