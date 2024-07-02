package edu.blackcat.clustering.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Getter
public class Route {
    private final Sensor startNode;
    private final Sensor endNode;
    private final List<Edge> edgePath;

    public Route(Sensor start, Sensor end) {
        this.startNode = start;
        this.endNode = end;
        this.edgePath = new ArrayList<>();
    }

    public void addEdge(Sensor sensor1, Sensor sensor2) {
        Edge edge = new Edge(sensor1, sensor2);
        this.edgePath.add(edge);
    }

    public void addEdge(Node node1, Node node2) {
        this.addEdge(node1.getCorrespondingSensor(), node2.getCorrespondingSensor());
    }

    @Override
    public String toString() {
        String output = String.format("Hops from %s to %s:\n", startNode.getPoint(), endNode.getPoint());
        StringJoiner joiner = new StringJoiner(", ");
        for (Edge edge : edgePath) {
            joiner.add(edge.toString());
        }
        output += joiner.toString();
        return output;
    }

    public boolean isComplete() {
        if (edgePath.isEmpty()) {
            return false;
        }
        Sensor finalSensor = edgePath.get(edgePath.size() - 1).getEnd();
        return endNode.equals(finalSensor);
    }
}
