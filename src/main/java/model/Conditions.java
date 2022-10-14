package model;

import java.util.HashMap;
import java.util.Map;

public class Conditions {
    // Declaring the static map
    public static final Map<Integer, String> conditionMap;

    // Instantiating the static map
    static {
        conditionMap = new HashMap<>();
        conditionMap.put(1, "clear");
        conditionMap.put(2, "windy");
        conditionMap.put(3, "mist");
        conditionMap.put(4, "drizzle");
        conditionMap.put(5, "dust");
    }


}
