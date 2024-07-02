package edu.blackcat.clustering.data;

import edu.blackcat.clustering.util.Random;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class Graph {
    public static final int GRAPH_WIDTH = 20;
    public static final int GRAPH_HEIGHT = 20;
    public static final int CLUSTER_WIDTH = 5;
    public static final int CLUSTER_HEIGHT = 5;

    List<Cluster> clusters;
    List<Sensor> sensors;

    public Graph() {
        this.sensors = new ArrayList<>();
        /* initialize clusters according to graph size and cluster size */
        this.clusters = this.partition();
    }

    /**
     * Insert a new list of sensors into the network
     *
     * @param sensors A list of configured sensors
     */
    public void insert(List<Sensor> sensors) {
        this.sensors.addAll(sensors);
        /* assign clusters to sensors */
        this.sensors = sensorsWithAssignedCluster(this.sensors);
    }

    /**
     * Scans through all the nodes in our world to see if any Sensors exist at a given point
     *
     * @param point coordinates that we want to see if we can find a sensor at
     * @return A sensor if one is found, null if there does not exist a sensor.
     */
    public Sensor findSensor(Point point) {
        for (Sensor sensor : this.sensors) {
            if (point.equals(sensor.getPoint())) {
                return sensor;
            }
        }
        return null;
    }

    /**
     * Build a greedy route that communications must hop through to communicate through from start to end
     *
     * @param start Sensor we must begin routing at
     * @param end   Sensor we must end routing at
     * @return a route containing a list of hops if the route is possible.
     */
    public Route mapRoute(Sensor start, Sensor end) {
        Route route = new Route(start, end);
        Node endNode = new Node(end);
        Node currentNode = new Node(start);
        currentNode.visit();
        do {
            // Find our nodes neighbors and best hop
            Node bestHop = new Node();
            currentNode.discoverNeighborsAndBestHop(sensors);

            //Greedy, if we can go right to the end do it.
            if (currentNode.getNeighbors().contains(endNode)) {
                bestHop = endNode;
            } else
                // Move to our next node and calculate meta
                if (currentNode.getClosestNeighbor() != null) {
                    bestHop = currentNode.getClosestNeighbor();
                }

            route.addEdge(currentNode, bestHop);
            currentNode = bestHop;
            currentNode.visit();

            //Check to see if we just jumped into a dead end
            currentNode.discoverNeighborsAndBestHop(sensors);

        } while (!currentNode.getCorrespondingSensor().equals(end) && currentNode.getNeighbors().size() != 0);


        return route;
    }

    /**
     * <h2><em>Huge disclaimer</em></h2>
     * We will assume rectangular world, meaning each {@link Cluster}'s boundary is clusterId * width, similar for height
     */
    private List<Cluster> partition() {
        List<Cluster> clusters = new ArrayList<>();
        int numClusters = (GRAPH_HEIGHT * GRAPH_WIDTH) / (CLUSTER_HEIGHT * CLUSTER_WIDTH);
        for (int i = 0; i < numClusters; i++) {
            Cluster generatedCluster = new Cluster(i);
            clusters.add(generatedCluster);
        }
        clusters.sort(Comparator.comparingInt(Cluster::getId));
        return clusters;
    }

    private List<Sensor> sensorsWithAssignedCluster(List<Sensor> sensors) {
        List<Sensor> assignedSensors = new ArrayList<>();
        for (Sensor sensor : sensors) {

            // Recreate this list for every sensor as borders are unique to each sensor
            List<Cluster> borderlineClusters = new ArrayList<>();
            for (Cluster cluster : clusters) {
                if (cluster.getArea().contains(sensor.getPoint())) {
                    assignedSensors.add(cluster.addSensorAndShareIt(sensor));
                } else if (cluster.getArea().hasBorder(sensor.getPoint())) {
                    //If this sensor lands on the border of a cluster, add it to a list and we'll randomly assign it to
                    // one of the clusters later
                    borderlineClusters.add(cluster);
                }
            }

            // If we have border cases randomly pick one of the clusters and assign the node to it
            if (!borderlineClusters.isEmpty()) {
                assignedSensors.add(
                        clusters.get(Random.get(borderlineClusters.size())).addSensorAndShareIt(sensor));
            }
        }
        return assignedSensors;
    }
}
