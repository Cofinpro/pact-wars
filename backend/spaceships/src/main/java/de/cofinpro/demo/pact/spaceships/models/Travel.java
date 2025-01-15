package de.cofinpro.demo.pact.spaceships.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Travel {

    private Long spaceshipId;
    private Long toPlanetId;
    private List<Long> personIds = new ArrayList<>();

    public Travel() {
    }

    public Travel(Long spaceshipId, Long toPlanetId, List<Long> personIds) {
        this.spaceshipId = spaceshipId;
        this.toPlanetId = toPlanetId;
        this.personIds = personIds;
    }

    public Long getSpaceshipId() {
        return spaceshipId;
    }

    public void setSpaceshipId(Long spaceshipId) {
        this.spaceshipId = spaceshipId;
    }

    public Long getToPlanetId() {
        return toPlanetId;
    }

    public void setToPlanetId(Long toPlanetId) {
        this.toPlanetId = toPlanetId;
    }

    public List<Long> getPersonIds() {
        return personIds;
    }

    public void setPersonIds(List<Long> personIds) {
        this.personIds = personIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Travel travel = (Travel) o;
        return Objects.equals(spaceshipId, travel.spaceshipId) &&
                Objects.equals(toPlanetId, travel.toPlanetId) &&
                Objects.equals(personIds, travel.personIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spaceshipId, toPlanetId, personIds);
    }

    @Override
    public String toString() {
        return "Travel{" +
                "spaceshipId=" + spaceshipId +
                ", toPlanetId=" + toPlanetId +
                ", personIds=" + personIds +
                '}';
    }
}
