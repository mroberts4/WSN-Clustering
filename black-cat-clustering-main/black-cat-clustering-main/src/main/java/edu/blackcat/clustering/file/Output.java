package edu.blackcat.clustering.file;

import edu.blackcat.clustering.data.Cluster;
import edu.blackcat.clustering.data.Graph;
import edu.blackcat.clustering.data.Sensor;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Output {
    public static final String OUTPUT_FILE = "network.txt";

    /**
     * Save sensor configuration data for a wireless sensor network. Output data is saved in {@link Output}'s OUTPUT_FILE field.
     *
     * @param graph Wireless Sensor Network
     */
    public static void saveNetwork(Graph graph) {
        try {
            PrintStream writer = new PrintStream(OUTPUT_FILE);
            writer.println(graph.getSensors().size());
            // Write out `input.txt` style network list
            for (Sensor sensor : graph.getSensors()) {
                writer.println(sensor.toString());
            }

            // Write out cluster information

            for (Cluster cluster : graph.getClusters()) {
                writer.println(cluster.toString());
            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
