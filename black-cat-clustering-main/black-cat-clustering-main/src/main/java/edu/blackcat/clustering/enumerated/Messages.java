package edu.blackcat.clustering.enumerated;

import lombok.Getter;

public enum Messages {
    Heading("// *************************************************************\n" +
            "// * Author: BLACK-CAT { Ethan Clark & Mariah Roberts }\n" +
            "// * Assignment Programming 1, Clustering\n" +
            "// * Course: COSC 3373\n" +
            "// * Instructor: Dr. Maha Shamseddine\n" +
            "// *************************************************************"),
    Menu("Please select BLACK-CAT Cluster experience mode.\nPlease type 'user' or 'random' and press enter.\n:: "),
    BadMenu("User selection is not valid. Program ending. Good bye :)"),
    Routing("Please pick a starting node and a ending node. The experience will generate a 'good enough' path using a greedy algorithm."),
    ShortRouting("Enter the starting node and ending node coordinates  according`x1 y1 x2 y2`\n:: "),
    NodeEnterError("Node coordinates were not entered correctly. Please try again."),
    SensorFindError("Could not find a node in the world that matches entered coordinates."),
    Success("Wow, that was a *great* path"),
    Failure("Darn, the connection you desire cannot be made"),
    Loop("Would you like to plot another route ['y'], or exit [any other input]? \n:: "),
    Goodbye("Thank you for playing with sensors, see you another time! Bye bye.");

    @Getter
    private final String message;

    Messages(String message) {
        this.message = message;
    }
}
