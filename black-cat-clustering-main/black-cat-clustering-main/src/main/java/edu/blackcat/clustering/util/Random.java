package edu.blackcat.clustering.util;

public class Random {
    /**
     * @param min Lowest random value
     * @param max Highest random value
     * @return random integer between (inclusive) min and max
     */
    public static int get(int min, int max) {
        java.util.Random random = new java.util.Random();
        return (random.nextInt(max - min) + min);
    }

    /**
     * @param max Highest random value
     * @return random integer between (inclusive) 0 and max
     */
    public static int get(int max) {
        return get(0, max);
    }

    /**
     * Flip a coin! 50/50 chance of getting a random integer of 0 or a random integer of 1
     *
     * @return true or false with a 50% chance of both!
     */
    public static boolean coinFlip() {
        return get(1) == 1;
    }
}
