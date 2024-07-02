package edu.blackcat.clustering.data;

import edu.blackcat.clustering.util.Random;
import lombok.Data;

import java.util.List;

import static edu.blackcat.clustering.data.Graph.GRAPH_HEIGHT;
import static edu.blackcat.clustering.data.Graph.GRAPH_WIDTH;

@Data
public class Sensor {
    public static final int RANGE_MIN = 1;
    public static final int RANGE_MAX = 8;
    public static final int ENERGY_MIN = 1;
    public static final int ENERGY_MAX = 100;
    public static final int PROCESSING_MIN = 1;
    public static final int PROCESSING_MAX = 100;

    private Point point;
    private int range;
    private int energyLevel;
    private int processingPower;
    private List<Sensor> neighbors;
    private List<Edge> connections;
    private int cluster;
    private Node correspondingNode;

    public Sensor() {
        this(
                Random.get(GRAPH_WIDTH),
                Random.get(GRAPH_HEIGHT),
                Random.get(RANGE_MIN, RANGE_MAX),
                Random.get(ENERGY_MIN, ENERGY_MAX),
                Random.get(PROCESSING_MIN, PROCESSING_MAX)
        );
    }

    public Sensor(int x, int y, int range, int energyLevel, int processingPower) {
        this.point = new Point(x, y);
        this.range = range;
        this.energyLevel = energyLevel;
        this.processingPower = processingPower;
    }

    /**
     * Generates a formatted string of non-connection meta-data of each sensor.
     * The sensors position, active radio range, energy level and processing power are reported.
     *
     * @return space seperated string of specs
     */
    public String toString() {
        return String.format("%d %d %d %d %d", this.point.getX(), this.point.getY(), this.range, this.energyLevel, this.processingPower);
    }

    /**
     * Evaluates whether this sensor and another sensor have the same id
     *
     * @param other
     * @return
     */
    public boolean equals(Sensor other) {
        return this.getPoint().equals(other.getPoint());
    }
}
