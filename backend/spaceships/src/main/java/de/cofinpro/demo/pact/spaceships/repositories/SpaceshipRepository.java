package de.cofinpro.demo.pact.spaceships.repositories;

import de.cofinpro.demo.pact.spaceships.models.Spaceship;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SpaceshipRepository {

    @Inject
    EntityManager entityManager;

    public List<Spaceship> listAllSpaceships() {
        return entityManager.createNamedQuery("Spaceship.findAll", Spaceship.class)
                .getResultList();
    }

    public List<Spaceship> listSpaceshipsOnPlanet(Long planetId) {
        return entityManager.createNamedQuery("Spaceship.findAllOnPlanet", Spaceship.class)
                .setParameter("planetId", planetId)
                .getResultList();
    }

    @Transactional
    public void moveSpaceshipToPlanet(Long spaceshipId, Long planetId) {
        Spaceship ship = entityManager.find(Spaceship.class, spaceshipId);
        ship.setOnPlanetId(planetId);
    }

}
