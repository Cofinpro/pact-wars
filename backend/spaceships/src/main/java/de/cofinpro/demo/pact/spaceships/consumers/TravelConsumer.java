package de.cofinpro.demo.pact.spaceships.consumers;

import de.cofinpro.demo.pact.spaceships.models.Travel;
import de.cofinpro.demo.pact.spaceships.repositories.SpaceshipRepository;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TravelConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    ManagedExecutor managedExecutor;

    @Inject
    SpaceshipRepository spaceshipRepository;

    @Incoming("spaceship-service")
    public void travelled(JsonObject jsonObject) {
        logger.info("Processing a travel!");
        Travel travel = jsonObject.mapTo(Travel.class);
        logger.info("Spaceship {} travelled to planet {}",
                travel.getSpaceshipId(), travel.getToPlanetId());
        // Must run blocking operation in a worker thread - we are in an IO Thread
        managedExecutor.runAsync(
                () -> spaceshipRepository.moveSpaceshipToPlanet(travel.getSpaceshipId(), travel.getToPlanetId()));
    }

}
