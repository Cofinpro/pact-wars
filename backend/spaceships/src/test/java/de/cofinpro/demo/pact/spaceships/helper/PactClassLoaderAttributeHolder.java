package de.cofinpro.demo.pact.spaceships.helper;

import java.util.HashMap;
import java.util.Map;

public class PactClassLoaderAttributeHolder {
    private static Map<String, Object> instanceMap;

    public static Map<String, Object> getInstanceMap() {
        if (instanceMap == null) {
            instanceMap = new HashMap<>();
        }
        return instanceMap;
    }
}