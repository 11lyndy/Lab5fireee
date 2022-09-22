import java.util.ArrayList;

public class ConvexHull {
    static int orientation(Point p1, Point p2, Point p3) {
        int val = (p2.y - p1.y) * (p3.x - p2.x) -
                (p2.x - p1.x) * (p3.y - p2.y);

        if (val == 0) return 0;  // collinear

        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    public static ArrayList<Point> selectConvexHull(Point points[], int n)
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

        int p = l, q;
        do
        {
            hull.add(points[p]);
            q = (p + 1) % n;

            for (int i = 0; i < n; i++)
            {
                /* If i is more counterclockwise than
                 current q, then update q */
                if (orientation(points[p], points[i], points[q])
                        == 2)
                    q = i;
            }
            p = q;

        } while (p != l);

        return hull;
    }
}
