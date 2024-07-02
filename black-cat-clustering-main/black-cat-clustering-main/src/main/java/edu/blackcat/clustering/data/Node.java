package edu.blackcat.clustering.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static edu.blackcat.clustering.util.Calculator.distance;

@Getter
public class Node {
    private final boolean seen;
    private boolean visited;
    private final Sensor correspondingSensor;
    private final List<Node> neighbors;
    private Node closestNeighbor;

    public Node(Sensor sensor) {
        this.seen = false;
        this.visited = false;
        sensor.setCorrespondingNode(this);
        this.correspondingSensor = sensor;
        this.neighbors = new ArrayList<>();
    }

    public Node() {
        this.seen = false;
        this.visited = false;
        this.correspondingSensor = new Sensor();
        this.neighbors = new ArrayList<>();
    }

    public void discoverNeighborsAndBestHop(List<Sensor> worldSensors) {
        double distanceMax = this.correspondingSensor.getRange();
        double distanceClosest = Integer.MAX_VALUE;

        for (Sensor sensor : worldSensors) {
            if (sensor.getCorrespondingNode() == null) {
                sensor.setCorrespondingNode(new Node(sensor));
            }

            if (!sensor.equals(this.correspondingSensor) && !sensor.getCorrespondingNode().isVisited()) {
                double sensorDistance = distance(this.correspondingSensor.getPoint(), sensor.getPoint());
                if (sensorDistance <= distanceMax) {
                    Node sensorNode = sensor.getCorrespondingNode();

                    this.neighbors.add(sensorNode);
                    if (sensorDistance < distanceClosest) {
                        distanceClosest = sensorDistance;
                        closestNeighbor = sensorNode;
                    }

                }
            }
        }
    }

    public void visit() {
        this.visited = true;
    }

    public boolean equals(Node other) {
        if (other.getCorrespondingSensor() == null || this.getCorrespondingSensor() == null) {
            return false;
        }
        return other.getCorrespondingSensor().equals(this.getCorrespondingSensor());
    }
}
