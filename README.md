black-cat-clustering
Ethan Clark & Mariah Roberts

Overview
This project was created as a part of Dr. Shamseddine's 'Wireless Sensor Networks' course at Texas A&M Corpus Christ. The goal of this assignment is to build a WSN simulated topography. This simulated world contains either a user determined list of sensors or a randomly generated world of sensors.

After building a world of sensors, the simulation will allow the user to select two nodes, a starting point and an ending point for a communication. The nodes will follow a "greedy algorithm" that will only communicate with the nearest non-visited node. This algorithm for communication performs very poorly. Many of the randomly generated nodes are distributed with weak signal strengths and / or distributed in isolated island clusters. Many of the network communications requested fail to generate a path using this algorithm. One modification has been made to the original assigned algorithm where if a node is able to communicate with the end node, it will communicate with it directly, instead of communicating with the closest node. This modification was made since the closest node to an end node may have another neighbor that is closer, and our communication would never reach the end node.

Technical overview
This project is built in Java using the Springboot framework. Components exist as either a Springboot service, component, or static utility methods. Interactions with the user via terminal take place in the Interaction Manager class, this class also coordinates with the remaining infrastructure to build out elements of a simulated WSN.

Cluster
Sensor
Route
As well as graph logic elements

Graph
Area
Node
Point
Edge
Operation
This project requires Java 8 and JDK 18, as well as a connection with maven, where dependencies (Spring) are downloaded from. Environmental variables for java home and will need to be set on the operating machine. https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html

On Windows set environment variables in powershell run env:JAVA_HOME = 'path to where JDK was installed'.
On Mac set environment variables temporarily using export export JAVA_HOME='path to where JDK was installed'.
To build the project you can utilize gradlew

To run automatically
Enroll for an education license for IntelliJ Ultimate at: https://www.jetbrains.com/community/education/#students and download/install IntelliJ Ultimate.

Import the project into IntelliJ and click the "play button" in ClusteringApplication.java

To run manually
./gradlew clean build

After the project is built, the program can be run using ./gradlew bootJar

The generated jar can be ran using docker or any other containerization application.

Assignment Directions below
Objectives
To reinforce the basic concepts of wireless sensor networks.
To simulate a clustered WSN.
To work with basic routing in WSN.
Basic Information
Due: Wednesday, April 6
Grading:
Program Correctness: 75 Program Design: 15 Style and Documentation: 10

Problem Statement
Make sure that you follow a good programming style. In your programs include a header with the following information:

// *************************************************************
// * Author: your full name
// * Assignment AssignmetNumber
// * Course: COSC 3373
// * Instructor: Dr. Maha Shamseddine
// *************************************************************
Problem:
In this assignment, you are simulating the operation of a WSN. The network covers a 20mx20m square area divided into uniform predetermined clusters of 5mx5m (i.e. total of 16 clusters). Your program will operate in two modes; random mode and user mode. In the random mode, the program will generate a random number of nodes between 10-100. If in the random mode, every node will have the following randomly generated characteristics:

(x,y) coordinates; x=0-20 and y=0-20
Radio range R; R=1-8 m
Energy level E; E=1-100
Processing Power P; P=1-100
According to the coordinates, every node is assigned to one of the predetermined clusters that includes these coordinates (i.e. in case of a coordinate on the border of some clusters, randomly assign this node to any of the neighboring clusters). A clusterhead out of the residents of the cluster is elected based on the following formula; F = 0.4R + 0.4E + 0.2P, the node with the largest F is the clusterhead. In the case of a tie, elect the nearest node to the center of the cluster.

Your simulation should allow a GREEDY intuitive routing. A node will always route packets to the nearest node, within its radio range, to the destination.

Your program will interact with the user via the command line. The user should choose between user mode and random mode. If user mode is chosen, then the program reads the number of nodes and their corresponding characteristics from input.txt that is organized in the following manner:

Number of nodes n
x1 y1 R1 E1 P1
x2 y2 R2 E2 P2
.
.
.
xn yn Rn En Pn
For example

3
3 1 4 40 30
2 7 5 99 55
13 14 4 45 80
This means that there are 3 nodes with node 1 having (3,1) coordinates, R=4, E=40, and P=30; similarly for the other nodes.

If the user chooses the random mode, then your program will randomly generate the number and characteristics of nodes.

In either mode, the program will output the characteristics of the network to output file network.txt following the same format of input.txt. In addition, the program will output to the screen and to network.txt the residency of every cluster (i.e. which nodes belong to the cluster) and its elected clusterhead. Use your own simple but easily readable format.

After choosing the mode of operation, the user should be able to enter a source and a destination for the program to route using the aforementioned routing technique. The resulted route should be output to the screen as a series of hops using the id of the node. The input menu should be displayed until the user chooses to quit. With every run, your program should use a clean copy of network.txt.

Deliverables
Working software program including all source files, make file, and readme file. Do not include input.txt in your submission as I will be using a script to test your program.
