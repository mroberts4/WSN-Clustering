package edu.blackcat.clustering.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static edu.blackcat.clustering.data.Graph.*;
import static edu.blackcat.clustering.util.Random.coinFlip;
import static java.lang.Math.*;

@Getter
public class Cluster {
    private final int id;
    private final Area area;
    private final List<Sensor> sensors;
    private double clusterHeadStrength;
    private double clusterHeadDistance;
    private int clusterHeadIndex;
    private Sensor clusterHead;

    public Cluster(int id) {
        this.sensors = new ArrayList<>();
        this.clusterHeadStrength = 0;
        this.clusterHeadDistance = 400;
        this.id = id;
        this.area = this.squareArea(id);
    }

    /**
     * Canonical a sensor by assigning a shallow copy to the cluster and updating the sensor to refer to being a member of the cluster
     * This is <em> not </em> memory efficient... but we're using Java, so we never really were efficient in the first place.
     *
     * @param sensor Original Sensor, this object will be copied
     * @return Updated copy of the parameter sensor that contains cluster links
     */
    public Sensor addSensorAndShareIt(Sensor sensor) {
        Sensor shallowCopy = sensor;
        shallowCopy.setCluster(this.getId());
        this.sensors.add(shallowCopy);
        if (shouldBeClusterHead(sensor)) {
            this.clusterHeadStrength = this.measureSignalStrength(sensor);
            this.clusterHeadDistance = this.calculateSensorDistance(sensor);
            this.clusterHead = sensor;
            this.clusterHeadIndex = this.sensors.size() - 1;
        }
        return shallowCopy;
    }

    /**
     * Generates a summary message describing Cluster's Sensor network and Cluster head.
     *
     * @return String containing cluster metadata
     */
    public String toString() {
        String output = String.format("CLUSTER-ID: %d\n", this.getId());

        // The area for each node ID never changes, so we don't need to print this out. But useful for troubleshooting
        // output += String.format("Area: %s\n", this.getArea());

        if ((this.getClusterHead() != null) && (!this.getSensors().isEmpty())) {
            StringJoiner joiner = new StringJoiner(", ", "{", "}\n");
            output += String.format("CLUSTER-HEAD: %s\nSENSORS: ", this.getClusterHead().getPoint().toString());
            for (Sensor sensor : this.getSensors()) {
                joiner.add(sensor.getPoint().toString());
            }
            output += joiner.toString();

        } else {
            output += "NO SENSORS ASSIGNED\n";
        }
        return output;
    }

    private Area squareArea(int id) {
        int rowLength = GRAPH_WIDTH / CLUSTER_WIDTH;
        int row = id % rowLength;
        int column = (int) floor(id / rowLength);
        return new Area(row, column);
    }

    private boolean shouldBeClusterHead(Sensor sensor) {
        if (measureSignalStrength(sensor) < this.clusterHeadStrength)
            return false;
        if (calculateSensorDistance(sensor) == this.clusterHeadDistance)
            return coinFlip();
        return true;
    }

    private double measureSignalStrength(Sensor sensor) {
        return 0.4 * sensor.getRange() + 0.4 * sensor.getEnergyLevel() + 0.2 * sensor.getProcessingPower();
    }

    private double calculateSensorDistance(Sensor sensor) {
        double epicenter_x = CLUSTER_WIDTH / 2;
        double epicenter_y = CLUSTER_HEIGHT / 2;

        return sqrt(abs(pow(sensor.getPoint().getX() - epicenter_x, 2) + pow(sensor.getPoint().getY() - epicenter_y, 2)));
    }
}
