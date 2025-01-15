package de.cofinpro.planets.controllers;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.filter.InteractionFilter;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFilter;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import de.cofinpro.planets.clients.SpaceshipsClient;
import de.cofinpro.planets.clients.models.Spaceship;
import de.cofinpro.planets.messaging.MessageSender;
import de.cofinpro.planets.models.Planet;
import de.cofinpro.planets.repositories.PlanetsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest   // No need for full Spring context of @SpringBootTest - testing only API
// Filter the pacts, only those for us as provider
@Provider("planets-rest")
// Filter the pacts, only those coming to the Controller under test
@PactFilter(value = "^\\/planets.*", filter = InteractionFilter.ByRequestPath.class)
// Load pacts from this source
//@PactFolder("pacts")
@PactBroker(host="localhost", port="8005")
class PlanetsControllerTest {

    // The states we support
    private static final String STATE_WITH_PLANETS = "i have a list of planets";
    private static final String STATE_NO_PLANETS = "i have no planets";
    private static final String STATE_VALID_TRAVEL = "i have planet 1 with spaceship 2 and person 5";
    private static final String STATE_INVALID_TRAVEL = "i have planet 1 without spaceship 2 or person 5";

    // We will send requests against our MockMvc
    @Autowired
    private MockMvc mockMvc;

    // Mock away the business logic - testing API only
    @MockBean
    private PlanetsRepository planetsRepository;

    @MockBean
    private MessageSender messageSender;

    @MockBean
    private SpaceshipsClient spaceshipsClient;

    // STATE methods - mock state accordingly

    @State(STATE_WITH_PLANETS)
    void stateWithPlanets() {
        when(planetsRepository.findAll()).thenReturn(List.of(
                new Planet(1L, "Planet 1", "Description for Planet 1", "http://someimage.png"),
                new Planet(2L, "Planet 2", "Description for Planet 2", "http://someimage.png"),
                new Planet(3L, "Planet 3", "Description for Planet 3", "http://someimage.png")
        ));
    }

    @State(STATE_NO_PLANETS)
    void stateNoPlanets() {
        when(planetsRepository.findAll()).thenReturn(Collections.emptyList());
    }

    @State(STATE_VALID_TRAVEL)
    void stateValidTravel() {
        when(planetsRepository.findAll()).thenReturn(List.of(
                new Planet(1L, "Planet 1", "Description for Planet 1", "http://someimage.png"),
                new Planet(2L, "Planet 2", "Description for Planet 2", "http://someimage.png"),
                new Planet(3L, "Planet 3", "Description for Planet 3", "http://someimage.png")
        ));
        when(spaceshipsClient.getSpaceshipsOnPlanet(1)).thenReturn(List.of(
                new Spaceship(1L, "Spaceship 1"),
                new Spaceship(2L, "Spaceship 2")
        ));
        // Hint: We would have to mock persons too when actually reading them
    }

    @State(STATE_INVALID_TRAVEL)
    void stateInvalidTravel() {
        when(planetsRepository.findAll()).thenReturn(List.of(
                new Planet(1L, "Planet 1", "Description for Planet 1", "http://someimage.png"),
                new Planet(2L, "Planet 2", "Description for Planet 2", "http://someimage.png"),
                new Planet(3L, "Planet 3", "Description for Planet 3", "http://someimage.png")
        ));
        when(spaceshipsClient.getSpaceshipsOnPlanet(1)).thenReturn(List.of(
                new Spaceship(1L, "Spaceship 1")
        ));
    }

    // Tell Pact to fire requests against our MockMvc
    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new MockMvcTestTarget(mockMvc));
    }

    // Will call this for each interaction that passes the filters in the class annotations
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

}