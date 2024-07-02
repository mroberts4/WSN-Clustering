package edu.blackcat.clustering.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Point {
    int x;
    int y;

    public boolean equals(Point other) {
        return (x == other.getX() && y == other.getY());
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
