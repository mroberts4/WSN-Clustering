package edu.blackcat.clustering.terminal;

import edu.blackcat.clustering.data.Graph;
import edu.blackcat.clustering.data.Point;
import edu.blackcat.clustering.data.Route;
import edu.blackcat.clustering.data.Sensor;
import edu.blackcat.clustering.file.Input;
import edu.blackcat.clustering.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static edu.blackcat.clustering.enumerated.Messages.*;
import static edu.blackcat.clustering.file.Output.saveNetwork;

@Component
public class InteractionManager {
    private static final String USER = "user";
    private static final String RANDOM = "random";
    private static final char YES = 'y';
    private static final int MIN_RANDOM_SENSORS = 10;
    private static final int MAX_RANDOM_SENSORS = 100;
    private final Input input;
    private final Reader reader;

    public InteractionManager(Input input, Reader reader) {
        this.input = input;
        this.reader = reader;
    }

    /**
     * Main loop for application, handles UX flow.
     * Prompts user on who we are, and what we're doing.
     *
     * Responsible for receiving and processing user terminal input as well as display terminal communications with user.
     */
    @Autowired
    public void onStart() {
        Graph world = requestAndBuildTheUsersWorld();
        // Write out our sensors to network.txt
        saveNetwork(world);
        try {
            System.out.println(this.input.readNetwork());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }


        // Request user input for routing
        System.out.println(Routing.getMessage());
        char loop = YES;
        do {
            searchForARoute(world);
            System.out.println(Loop.getMessage());
            loop = reader.readLine().toLowerCase(Locale.ROOT).charAt(0);
        } while (YES == loop);

        System.out.println(Goodbye.getMessage());
        throw new RuntimeException("I'm done running this time. lol");
    }

    private void searchForARoute(Graph world) {
        System.out.println(ShortRouting.getMessage());
        List<Point> pointsToRoute = this.input.readPoints();

        if (pointsToRoute == null || pointsToRoute.size() != 2) {
            System.out.println(NodeEnterError.getMessage());
        } else {
            Sensor startSensor = world.findSensor(pointsToRoute.get(0));
            Sensor endSensor = world.findSensor(pointsToRoute.get(1));
            if (startSensor == null || endSensor == null) {
                System.out.println(SensorFindError.getMessage());
            } else {
                Route route = world.mapRoute(startSensor, endSensor);

                if (route.isComplete()) {
                    System.out.println(Success.getMessage());
                    System.out.println(route.getEdgePath());
                } else {
                    System.out.println(Failure.getMessage());
                }
            }
        }
    }

    private Graph requestAndBuildTheUsersWorld() {
        Graph world = new Graph();
        System.out.println(Heading.getMessage());
        System.out.println(Menu.getMessage());

        String menuChoice = reader.readLine();
        if (USER.equals(menuChoice)) {
            try {
                world.insert(this.input.readSensors());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else if (RANDOM.equals(menuChoice)) {
            world.insert(generateRandomSensors());
        } else {
            System.out.println(BadMenu.getMessage());
            System.exit(0);
        }
        return world;
    }

    private List<Sensor> generateRandomSensors() {
        List<Sensor> sensors = new ArrayList<>();
        for (int i = 0; i < Random.get(MIN_RANDOM_SENSORS, MAX_RANDOM_SENSORS); i++) {
            sensors.add(new Sensor());
        }
        return sensors;
    }
}
