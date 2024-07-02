package edu.blackcat.clustering.util;

import edu.blackcat.clustering.data.Point;

public class Calculator {

    /**
     * Get the euclidean distance between two points
     * @param point1
     * @param point2
     * @return double object with value as the distance
     */
    public static double distance(Point point1, Point point2) {
        double inside = Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2);
        return Math.sqrt(Math.abs(inside));
    }
}
