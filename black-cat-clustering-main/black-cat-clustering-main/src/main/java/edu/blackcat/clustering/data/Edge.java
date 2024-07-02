package edu.blackcat.clustering.data;

import lombok.Getter;

@Getter
public class Edge {
    private final Sensor start;
    private final Sensor end;

    public Edge(Sensor start, Sensor end) {
        this.start = start;
        this.end = end;
    }

    public String toString() {
        return String.format("%s --> %s", start.getPoint(), end.getPoint());
    }
}
