package de.cofinpro.planets.models;

public class PlanetNotFoundException extends RuntimeException {
    public PlanetNotFoundException(Long id) {
        super("Could not find planet " + id);
    }
}
