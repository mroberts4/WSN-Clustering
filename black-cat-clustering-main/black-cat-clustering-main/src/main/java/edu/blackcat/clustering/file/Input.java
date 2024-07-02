package edu.blackcat.clustering.file;

import edu.blackcat.clustering.data.Point;
import edu.blackcat.clustering.data.Sensor;
import edu.blackcat.clustering.terminal.Reader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static edu.blackcat.clustering.file.Output.OUTPUT_FILE;

@Service
public class Input {
    private static final String INPUT_FILE = "input.txt";

    private final Reader reader;

    public Input(Reader reader) {
        this.reader = reader;
    }

    /**
     * Reads a line from terminal input and tries to format the input line to a set of 2 points
     * If the input is not properly formatted an empty list is returned.
     * If the input is properly formatted then each of the sets is transformed into a point object
     * These point objects are stored in a list and returned
     * @return List of points, either empty if bad input or containing 2 points if propeer input
     */
    public List<Point> readPoints() {
        List<String> lines = Arrays.asList(reader.readLine().split(" "));
        List<Point> points = new ArrayList<>();
        if (lines.size() != 4) {
            return points;
        }

        points.add(new Point(Integer.parseInt(lines.get(0)), Integer.parseInt(lines.get(1))));
        points.add(new Point(Integer.parseInt(lines.get(2)), Integer.parseInt(lines.get(3))));

        return points;
    }

    /**
     * Takes user input from {@link Input}'s INPUT_FILE and generates a list of sensors with matching specifications
     *
     * @return List of {@link Sensor} with non-connection configurations
     * @throws FileNotFoundException user input file deleted while processing
     */
    public List<Sensor> readSensors() throws FileNotFoundException {
        File inputFile = new File(INPUT_FILE);
        List<Sensor> sensors = new ArrayList<>();
        if (inputFile.exists() && inputFile.isFile()) {
            Scanner scanner = new Scanner(inputFile);
            int networkSize = scanner.nextInt();

            for (int i = 0; i < networkSize; i++) {
                Sensor readSensor = new Sensor();
                readSensor.setPoint(new Point(scanner.nextInt(), scanner.nextInt()));
                readSensor.setRange(scanner.nextInt());
                readSensor.setEnergyLevel(scanner.nextInt());
                readSensor.setProcessingPower(scanner.nextInt());

                sensors.add(readSensor);
            }
            scanner.close();
        }
        return sensors;
    }

    /**
     * Generates a string of the network's sensors and their non-connection configurations as read from {@link Output}'s OUTPUT_FILE
     *
     * @return Multi-line string specifying the Sensor count as well as configuration data
     * @throws FileNotFoundException If the output file is deleted while processing
     */
    public String readNetwork() throws FileNotFoundException {
        File inputFile = new File(OUTPUT_FILE);
        StringBuilder networkString = new StringBuilder();
        if (inputFile.exists() && inputFile.isFile()) {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNext()) {
                networkString.append(scanner.nextLine()).append('\n');
            }
        }
        return networkString.toString();
    }
}
