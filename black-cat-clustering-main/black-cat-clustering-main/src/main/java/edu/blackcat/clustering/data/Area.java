package edu.blackcat.clustering.data;

import static edu.blackcat.clustering.data.Graph.CLUSTER_HEIGHT;
import static edu.blackcat.clustering.data.Graph.CLUSTER_WIDTH;

public class Area {
    private final Point boundary_ul;
    private final Point boundary_ur;
    private final Point boundary_ll;
    private final Point boundary_lr;

    public Area(int row, int column) {
        this.boundary_ll = new Point(row * CLUSTER_WIDTH, column * CLUSTER_HEIGHT);
        this.boundary_lr = new Point((row + 1) * CLUSTER_WIDTH, column * CLUSTER_HEIGHT);
        this.boundary_ur = new Point((row + 1) * CLUSTER_WIDTH, (column + 1) * CLUSTER_HEIGHT);
        this.boundary_ul = new Point(row * CLUSTER_WIDTH, (column + 1) * CLUSTER_HEIGHT);
    }

    /**
     * Check to see if this point falls INSIDE our area compare it to our X edge and Y edge
     *
     * @param point location to determine if it is enclosed
     * @return returns whether the point is fully enclosed, if the point is on the edge false is returned
     */
    public boolean contains(Point point) {
        int x = point.getX();
        int y = point.getY();

        // Checking our left and right WIDTH boundaries
        if (x < boundary_ll.getX() || x > boundary_lr.getX()) {
            return false;
        }

        // Checking our top and bottom HEIGHT boundaries
        return y >= boundary_ll.getY() && y <= boundary_ul.getY();

    }

    /**
     * Determine if a point lands on an area's boundary line
     *
     * @param point A set of X Y coordinates that corresponds to a position on a graph
     * @return true if the point lands on a line outlining the area
     */
    public boolean hasBorder(Point point) {
        int x = point.getX();
        int y = point.getY();

        // Does this point land ON our TOP or BOTTOM altitude?
        if (y == boundary_ur.getY() || y == boundary_ll.getY()) {

            // Does this point land IN our LEFT and RIGHT longitude?
            return x >= boundary_ll.getX() && x <= boundary_lr.getX();
        } else {
            // Does this point land ON our LEFT or RIGHT longitude?
            if (x == boundary_ll.getX() || x == boundary_lr.getX()) {

                //Does this point land IN our TOP and BOTTOM latitude?
                return y <= boundary_ur.getY() && y >= boundary_ll.getY();
            }
        }
        return false;
    }

    public String toString() {
        return String.format("[%s, %s, %s, %s]", boundary_ul, boundary_ur, boundary_ll, boundary_lr);
    }
}
