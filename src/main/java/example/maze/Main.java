package example.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final BufferedReader READER =
        new BufferedReader(new InputStreamReader(System.in));

    public static int readInt(String query) throws IOException {
        System.out.print(query);
        String line = READER.readLine();
        return Integer.parseInt(line);
    }

    public static void main(String[] args) throws IOException {
        int length = readInt("미로 크기:");
        String[] lines = new String[length];
        for (int i = 0; i < length; i++)
            lines[i] = READER.readLine();
        MazeSolver solver = new MazeSolver(lines);
        Stack<Point> pointStack = new Stack<>();
        Point p;
        pointStack.push(p = new Point(0, 0));
        while (!pointStack.isEmpty()) {
            READER.readLine();
            solver.set(p = pointStack.peek(), MazeSolver.MazePoint.VISITED);
            System.out.println(solver);
            System.out.println(p);
            System.out.println();
            if (solver.isGoal(p))
                break;
            List<Point> adjacentList = solver.getAdjacentList(p);
            if (adjacentList.isEmpty()) {
                do {
                    solver.set(pointStack.pop(), MazeSolver.MazePoint.BLOCKED);
                    adjacentList = solver.getAdjacentList(p = pointStack.peek());
                } while (adjacentList.isEmpty());
            } else {
                for (Point adjacent : adjacentList)
                    pointStack.push(adjacent);
            }
        }
        if (solver.isGoal(p)) {
            System.out.println("성공");
        } else {
            System.out.println("넌 못 빠져나간다.");
        }
    }

    public static class MazeSolver {
        private final int length;
        private final MazePoint[][] board;

        public MazeSolver(String[] lines) throws IOException {
            this.length = lines.length;
            this.board = new MazePoint[this.length][this.length];
            for (int i = 0; i < lines.length; i++) {
                char[] chars = lines[i].toCharArray();
                if (chars.length != this.length)
                    throw new IOException("the number of characters does not match");
                for (int j = 0; j < chars.length; j++)
                    this.board[i][j] = MazePoint.fromChar(chars[j]);
            }
        }

        public boolean isGoal(Point p) {
            return p.row == this.length - 1 && p.col == this.length - 1;
        }

        public List<Point> getAdjacentList(Point p) {
            List<Point> list = new ArrayList<>();
            if (p.row > 0 && this.board[p.row - 1][p.col] == MazePoint.OPEN)
                list.add(new Point(p.row - 1, p.col));
            if (p.col > 0 && this.board[p.row][p.col - 1] == MazePoint.OPEN)
                list.add(new Point(p.row, p.col - 1));
            if (p.row < this.length - 1 && this.board[p.row + 1][p.col] == MazePoint.OPEN)
                list.add(new Point(p.row + 1, p.col));
            if (p.col < this.length - 1 && this.board[p.row][p.col + 1] == MazePoint.OPEN)
                list.add(new Point(p.row, p.col + 1));
            return list;
        }

        public void set(Point p, MazePoint point) {
            this.board[p.row][p.col] = point;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (MazePoint[] line : this.board) {
                for (MazePoint p : line)
                    builder.append(p.toChar());
                builder.append('\n');
            }
            return builder.toString();
        }

        public enum MazePoint {

            OPEN,
            CLOSED,
            VISITED,
            BLOCKED;

            public static MazePoint fromChar(char ch) {
                switch (ch) {
                    case '0':
                        return OPEN;
                    case '1':
                        return CLOSED;
                    case 'X':
                        return VISITED;
                    case 'V':
                        return BLOCKED;
                    default:
                        throw new IllegalArgumentException("unexpected character found");
                }
            }

            public char toChar() {
                switch (this) {
                    case OPEN:
                        return ' ';
                    case CLOSED:
                        return '|';
                    case VISITED:
                        return 'X';
                    case BLOCKED:
                        return 'V';
                }
                throw new IllegalStateException("unexpected enum found");
            }
        }
    }
}
