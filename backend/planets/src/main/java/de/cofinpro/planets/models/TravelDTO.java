package de.cofinpro.planets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representation of a travel for the REST-API
 */
public class TravelDTO {

    @NotNull
    private Long spaceshipId;

    @NotNull
    private Long toPlanetId;

    @Size(min = 1)
    private List<Long> personIds;

    // Old API, keeping for backwards compatibility
    @Deprecated
    private Long personId;

    public TravelDTO() {
    }

    public TravelDTO(Long spaceshipId, Long toPlanetId, List<Long> personIds) {
        this.spaceshipId = spaceshipId;
        this.toPlanetId = toPlanetId;
        this.personIds = personIds;
    }

    public TravelDTO(Long spaceshipId, Long toPlanetId, Long personId) {
        this.spaceshipId = spaceshipId;
        this.toPlanetId = toPlanetId;
        this.personId = personId;
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @JsonIgnore
    @AssertTrue
    public boolean isPersonValid() {
        // Validate that either a persons list is provided, or a single person
        return (personIds != null && personIds.size() > 0) || personId != null;
    }

    /**
     * @return The internal travel representation for this travel
     */
    public Travel toTravel() {
        if (personIds != null && personIds.size() > 1) {
            return new Travel(spaceshipId, toPlanetId, personIds);
        } else {
            return new Travel(spaceshipId, toPlanetId, Collections.singletonList(personId));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelDTO travelDTO = (TravelDTO) o;
        return Objects.equals(spaceshipId, travelDTO.spaceshipId) && Objects.equals(toPlanetId, travelDTO.toPlanetId) && Objects.equals(personIds, travelDTO.personIds) && Objects.equals(personId, travelDTO.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spaceshipId, toPlanetId, personIds, personId);
    }

    @Override
    public String toString() {
        return "TravelDTO{" +
                "spaceshipId=" + spaceshipId +
                ", toPlanetId=" + toPlanetId +
                ", personIds=" + personIds +
                ", personId=" + personId +
                '}';
    }
}
