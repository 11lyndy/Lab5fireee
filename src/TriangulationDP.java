import java.util.*;

public class TriangulationDP {

    static double dist(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x)
                + (p1.y - p2.y) * (p1.y - p2.y));
    }

    static double cost(Point points[], int i, int j, int k) {
        Point p1 = points[i], p2 = points[j],
                p3 = points[k];
        return dist(p1, p2) + dist(p2, p3) + dist(p3, p1);
    }

    static double minCost(Point points[], int n) {
        // There must be at least 3 points to form a
        // triangle
        if (n < 3) return 0;

        double[][] table = new double[n][n];
        int[][] traceTable = new int[n][n];

        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (j < i + 2)
                    table[i][j] = 0.0;
                else {
                    table[i][j] = 1000000.0;
                    for (int k = i + 1; k < j; k++) {
                        double val = table[i][k] + table[k][j] + cost(points, i, j, k);
                        if (table[i][j] >= val) {
                            traceTable[i][j] = k;
                            table[i][j] = val;
                        }
                    }
                }
            }
        }

        printKMap(traceTable, 0, n - 1);
        for (double[] row : table) {
            System.out.println(Arrays.toString(row));
        }
        return table[0][n - 1];
    }

    static void printKMap(int[][] traceTable, int i, int j) {
        if (traceTable[i][j] == 0) return;
        if (j < i + 2) return;

        System.out.println(i + " " + j + " " + traceTable[i][j]);
        printKMap(traceTable, i, traceTable[i][j]);
        printKMap(traceTable, traceTable[i][j], j);
    }

    public static void main(String[] args) {
        Point[] points = {new Point(0, 0), new Point(1, 0),
                new Point(2, 1), new Point(1, 2),
                new Point(0, 2)};

        int n = points.length;
        Point[] points1 = new Point[n];
        points1 = ConvexHull.selectConvexHull(points, n).toArray(points1);
        System.out.println(minCost(points1, n));
    }
}

// This code is contributed by Karandeep Singh


