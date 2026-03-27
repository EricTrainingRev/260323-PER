package org.revature;

import java.util.*;

public class TheCollections {
    /*
        The Collection API is how we refence the commonly used data structures
        provided by the JDK.
     */
    public static void main(String[] args) {
        /*
            The common syntax used for these data structures is to declare the
            interface as the type and then initialize an object of a class that
            implements that interface
         */
        List<String> collectionOfNames = new ArrayList<>();
        collectionOfNames.add("Billy");
        collectionOfNames.add("Sally");
        collectionOfNames.get(1);

        Set<Integer> collectionOfAges = new HashSet<>();
        collectionOfAges.add(25);
        collectionOfAges.add(34);

        Map<String, String> cars = new HashMap<>();
        cars.put("Ford", "Fusion");
        cars.put("Tesla", "Cybertruck");
    }

}
