package de.cofinpro.demo.pact.spaceships.resources;

import de.cofinpro.demo.pact.spaceships.models.Spaceship;
import de.cofinpro.demo.pact.spaceships.repositories.SpaceshipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

@ApplicationScoped
@Path("/spaceships")
public class SpaceshipResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    SpaceshipRepository spaceshipRepository;

    @GET
    @Produces("application/json")
    public List<Spaceship> getSpaceships(@QueryParam("planetId") Long planetId) {
        logger.info("Getting spaceships for planetId {}", planetId);
        if (planetId != null && planetId > 0) {
            return spaceshipRepository.listSpaceshipsOnPlanet(planetId);
        } else {
            return spaceshipRepository.listAllSpaceships();
        }
    }

}
