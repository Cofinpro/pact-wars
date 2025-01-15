package de.cofinpro.planets.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(schema = "planets", name = "planets")
public class Planet {

    private @Id Long id;
    private String name;
    private @Column(columnDefinition = "text") String description;
    private String image;

    public Planet() {}

    public Planet (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Planet(Long id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return Objects.equals(id, planet.id) &&
                Objects.equals(name, planet.name) &&
                Objects.equals(description, planet.description) &&
                Objects.equals(image, planet.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image);
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
