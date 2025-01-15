package de.cofinpro.planets.controllers;

import de.cofinpro.planets.clients.SpaceshipsClient;
import de.cofinpro.planets.clients.models.Spaceship;
import de.cofinpro.planets.messaging.MessageSender;
import de.cofinpro.planets.models.Planet;
import de.cofinpro.planets.models.PlanetNotFoundException;
import de.cofinpro.planets.models.Travel;
import de.cofinpro.planets.models.TravelDTO;
import de.cofinpro.planets.repositories.PlanetsRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
public class PlanetsController {

    private final Log log = LogFactory.getLog(PlanetsController.class);

    private final PlanetsRepository planetsRepository;
    private final MessageSender messageSender;
    private final SpaceshipsClient spaceshipsClient;

    public PlanetsController(PlanetsRepository planetsRepository, MessageSender messageSender, SpaceshipsClient spaceshipsClient) {
        this.planetsRepository = planetsRepository;
        this.messageSender = messageSender;
        this.spaceshipsClient = spaceshipsClient;
    }

    @GetMapping("/planets")
    public List<Planet> getPlanets() {
        return planetsRepository.findAll();
    }

    @GetMapping("/planets/{id}")
    public Planet getPlanetById(@PathVariable(value = "id") Long id) {
        return planetsRepository.findById(id)
                .orElseThrow(() -> new PlanetNotFoundException(id));
    }

    @PostMapping("/planets/{id}/travel")
    public String doTravel(@PathVariable(value = "id") @NotNull Long planetId,
                           @RequestBody @Valid TravelDTO travelDTO) {

        // Convert to our internal model
        Travel travel = travelDTO.toTravel();

        // Check if spaceship is on planet
        assertSpaceshipIsOnSourcePlanet(planetId, travel.getSpaceshipId());

        // HINT: should actually also check if person is on source planet (per REST)

        String message = String.format("Travel starting in planet %d: %s", planetId, travel);
        log.info(message);
        messageSender.sendTravel(travel);
        return message;
    }

    private void assertSpaceshipIsOnSourcePlanet(Long planetId, Long spaceshipId) {
        List<Spaceship> spaceships = spaceshipsClient.getSpaceshipsOnPlanet(planetId);
        if (spaceships.stream().noneMatch(s -> Objects.equals(s.getId(), spaceshipId))) {
            throw new NotOnPlanetException(String.format("Spaceship %d is NOT on origin planet %d", spaceshipId, planetId));
        }
    }

}
