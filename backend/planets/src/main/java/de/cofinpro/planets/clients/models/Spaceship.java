package de.cofinpro.planets.clients.models;

import java.util.Objects;

/**
 * This is a REDUCED version of Spaceship: only that which we need in this service!
 */
public class Spaceship {

    private Long id;

    private String name;

    public Spaceship() {
    }

    public Spaceship(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spaceship spaceship = (Spaceship) o;
        return Objects.equals(id, spaceship.id) &&
                Objects.equals(name, spaceship.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Spaceship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
