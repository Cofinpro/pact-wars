package de.cofinpro.demo.pact.spaceships.resources;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.filter.InteractionFilter;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFilter;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import de.cofinpro.demo.pact.spaceships.helper.AbstractPactTest;
import de.cofinpro.demo.pact.spaceships.models.Spaceship;
import de.cofinpro.demo.pact.spaceships.repositories.SpaceshipRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * HINT: because Quarkus uses its own ClassLoader for tests, the injected Beans are not available in
 * the Pact methods because these run with a different ClassLoader. So we use a workaround to pass
 * the State to the test method itself, where the mocking can take place. This functionality is
 * implemented in AbstractPactTest.
 *
 * See also: https://github.com/quarkusio/quarkus/issues/22611
 */
@QuarkusTest
@Provider("spaceships-rest")
@PactFilter(value = "^\\/spaceships.*", filter = InteractionFilter.ByRequestPath.class)
//@PactFolder("pacts")
@PactBroker(host="localhost", port="8005")
class SpaceshipResourceTest extends AbstractPactTest {

    @InjectMock
    SpaceshipRepository spaceshipRepository;

    @BeforeEach
    void before(PactVerificationContext context) {
        if (context != null) {
            // Set the target to port 8081 (default Quarkus test port)
            HttpTestTarget testTarget = new HttpTestTarget("localhost", 8081);
            context.setTarget(testTarget);
        }
    }

    // Possible states defined by our consumers
    private static final String STATE_HAS_SHIPS_ON_PLANET_1 = "has ships on planet 1";
    private static final String STATE_HAS_NO_SHIPS_ON_PLANET_2 = "has no ships on planet 2";
    private static final String STATE_HAS_SHIPS = "i have a list of spaceships";
    private static final String STATE_HAS_NO_SHIPS = "i have no spaceships";

    @State({STATE_HAS_SHIPS, STATE_HAS_SHIPS_ON_PLANET_1})
    void stateHasShipsOnPlanet1() {
        // We don't actually mock anything here, but only set the state for later (ClassLoader problem, see class commentary)
        setState(STATE_HAS_SHIPS_ON_PLANET_1);
    }

    @State({STATE_HAS_NO_SHIPS, STATE_HAS_NO_SHIPS_ON_PLANET_2})
    void stateHasNoShipsOnPlanet2() {
        // We don't actually mock anything here, but only set the state for later (ClassLoader problem, see class commentary)
        setState(STATE_HAS_NO_SHIPS_ON_PLANET_2);
    }

    void initMocks() {
        // This is called from the test method, and will mock according to state
        String state = getState();
        switch (state) {
            case STATE_HAS_SHIPS:
            case STATE_HAS_SHIPS_ON_PLANET_1:
                when(spaceshipRepository.listAllSpaceships()).thenReturn(List.of(
                        new Spaceship(1L, "X-Fling", "Consumer attack fighter", "http://some.url.de/image.jpg", 1L),
                        new Spaceship(2L, "TRY-Fighter", "Provider attack fighter", "http://some.url.de/image.jpg", 1L),
                        new Spaceship(3L, "Millenium Bug", "Old but fast freighter", "http://some.url.de/image.jpg", 2L)
                ));
                when(spaceshipRepository.listSpaceshipsOnPlanet(anyLong())).thenReturn(List.of(
                        new Spaceship(1L, "X-Fling", "Consumer attack fighter", "http://some.url.de/image.jpg", 1L),
                        new Spaceship(2L, "TRY-Fighter", "Provider attack fighter", "http://some.url.de/image.jpg", 1L)
                ));
                break;
            case STATE_HAS_NO_SHIPS:
            case STATE_HAS_NO_SHIPS_ON_PLANET_2:
                when(spaceshipRepository.listAllSpaceships()).thenReturn(Collections.emptyList());
                when(spaceshipRepository.listSpaceshipsOnPlanet(anyLong())).thenReturn(Collections.emptyList());
                break;
            default:
                throw new IllegalStateException("Invalid state: " + state);
        }
    }

    // Will call this for each interaction that passes the filters in the class annotations
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        // We have to initialize the mocks here according to state (ClassLoader problem, see class commentary)
        initMocks();
        context.verifyInteraction();
    }

}