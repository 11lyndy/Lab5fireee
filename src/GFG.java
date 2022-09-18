import java.util.*;
public class GFG {// A Dynamic Programming based program to find minimum cost
// of convex polygon triangulation



        // Structure of a point in 2D plane
        static class Point {
            int x, y;
            Point(int x, int y)
            {
                this.x = x;
                this.y = y;
            }
        }

        // Utility function to find minimum of two double values
        static double min(double x, double y)
        {
            return (x <= y) ? x : y;
        }

        // A utility function to find distance between two
// points in a plane
        static double dist(Point p1, Point p2)
        {
            return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x)
                    + (p1.y - p2.y) * (p1.y - p2.y));
        }

        // A utility function to find cost of a triangle. The
// cost is considered as perimeter (sum of lengths of
// all edges) of the triangle
        static double cost(Point points[], int i, int j, int k)
        {
            Point p1 = points[i], p2 = points[j],
                    p3 = points[k];
            return dist(p1, p2) + dist(p2, p3) + dist(p3, p1);
        }

        // A Dynamic programming based function to find minimum
// cost for convex polygon triangulation.
        static double mTCDP(Point points[], int n)
        {
            // There must be at least 3 points to form a
            // triangle
            if (n < 3)
                return 0;

            // table to store results of subproblems.
            // table[i][j] stores cost of triangulation of
            // points from i to j. The entry table[0][n-1]
            // stores the final result.
            double[][] table = new double[n][n];
            int[][] tablek=new int[n][n];

            // Fill table using above recursive formula. Note
            // that the table is filled in diagonal fashion
            // i.e., from diagonal elements to table[0][n-1]
            // which is the result.
            for (int gap = 0; gap < n; gap++) {
                for (int i = 0, j = gap; j < n; i++, j++) {
                    if (j < i + 2)
                        table[i][j] = 0.0;
                    else {
                        table[i][j] = 1000000.0;
                        for (int k = i + 1; k < j; k++) {
                            double val
                                    = table[i][k] + table[k][j]
                                    + cost(points, i, j, k);
//                            table[i][j]=min(table[i][j],val);
                            if (table[i][j] >= val){
                                tablek[i][j]=k;
//                                System.out.println(i+" "+j+" "+k);
                                table[i][j] = val;}
                        }
                    }
                }
            }
//            for (int[] row: tablek) {
//                System.out.println(Arrays.toString(row));
//            }
            printKMap(tablek,0,n-1);
            for (double[] row: table) {
                System.out.println(Arrays.toString(row));
            }
            return table[0][n - 1];
        }
    static void printKMap(int[][] tablek,int i,int j){
        if(tablek[i][j] == 0){
            return;
        }
        if(j < i +2) return;
        System.out.println(i+" "+j+" "+tablek[i][j]);
        printKMap(tablek,i,tablek[i][j]);
        printKMap(tablek,tablek[i][j],j);
    }
    public static int orientation(Point p1, Point p2,
                                  Point p3)
    {
        // See 10th slides from following link
        // for derivation of the formula
        int val = (p2.y - p1.y) * (p3.x - p2.x) -
                (p2.x - p1.x) * (p3.y - p2.y);

        if (val == 0) return 0;  // collinear

        // clock or counterclock wise
        return (val > 0)? 1: 2;
    }
    public static ArrayList<Point> convexHull(Point points[], int n)
    {
        // There must be at least 3 points
        if (n < 3) return null;

        // Initialize Result
        ArrayList<Point> hull = new ArrayList<Point>();

        // Find the leftmost point
        int l = 0;
        for (int i = 1; i < n; i++)
            if (points[i].x < points[l].x)
                l = i;

        // Start from leftmost point, keep moving
        // counterclockwise until reach the start point
        // again. This loop runs O(h) times where h is
        // number of points in result or output.
        int p = l, q;
        do
        {
            // Add current point to result
            hull.add(points[p]);

            // Search for a point 'q' such that
            // orientation(p, q, x) is counterclockwise
            // for all points 'x'. The idea is to keep
            // track of last visited most counterclock-
            // wise point in q. If any point 'i' is more
            // counterclock-wise than q, then update q.
            q = (p + 1) % n;

            for (int i = 0; i < n; i++)
            {
                // If i is more counterclockwise than
                // current q, then update q
                if (orientation(points[p], points[i], points[q])
                        == 2)
                    q = i;
            }

            // Now q is the most counterclockwise with
            // respect to p. Set p as q for next iteration,
            // so that q is added to result 'hull'
            p = q;

        } while (p != l);  // While we don't come to first
        // point

//        // Print Result
//        for (Point temp : hull)
//            System.out.println("(" + temp.x + ", " +
//                    temp.y + ")");
        return hull;
    }


    // Driver program to test above functions
        public static void main(String[] args)
        {
            Point[] points = { new Point(0, 0), new Point(1, 0),
                    new Point(2, 1), new Point(1, 2),
                    new Point(0, 2) };

            int n = points.length;
            Point[] points1=new Point[n];
            points1=convexHull(points,n).toArray(points1);
            System.out.println(mTCDP(points1, n));
        }
    }

// This code is contributed by Karandeep Singh


