package de.cofinpro.planets.repositories;

import de.cofinpro.planets.models.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetsRepository extends JpaRepository<Planet, Long> { }
