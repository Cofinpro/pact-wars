package de.cofinpro.demo.pact.spaceships.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "spaceships")
@NamedQueries({
        @NamedQuery(name = "Spaceship.findAll", query = "select s from Spaceship s"),
        @NamedQuery(name = "Spaceship.findAllOnPlanet", query = "select s from Spaceship s WHERE s.onPlanetId = :planetId")
})
public class Spaceship {

    @Id
    private Long id;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private String image;

    @Column(name = "on_planet_id")
    private Long onPlanetId;

    public Spaceship() {
        // Empty constructor
    }

    public Spaceship(Long id, String name, String description, String image, Long onPlanetId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.onPlanetId = onPlanetId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getOnPlanetId() {
        return onPlanetId;
    }

    public void setOnPlanetId(Long onPlanetId) {
        this.onPlanetId = onPlanetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spaceship spaceship = (Spaceship) o;
        return Objects.equals(id, spaceship.id) &&
                Objects.equals(name, spaceship.name) &&
                Objects.equals(onPlanetId, spaceship.onPlanetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, onPlanetId);
    }

    @Override
    public String toString() {
        return "Spaceship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", onPlanetId=" + onPlanetId +
                '}';
    }
}
