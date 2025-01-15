package de.cofinpro.planets.clients;


import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import de.cofinpro.planets.clients.models.Spaceship;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import java.util.Collections;
import java.util.List;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArrayMinLike;
import static org.assertj.core.api.Assertions.assertThat;

@RestClientTest(
        properties = "spaceships.baseurl:http://localhost:49635",  // Tell Spring where to call pact mock-provider
        components = SpaceshipsClient.class)
@AutoConfigureWebClient(registerRestTemplate=true)    // We injected a RestTemplate directly
@AutoConfigureMockRestServiceServer(enabled = false)  // Do NOT start a Mock Server from Sprint, we have a Pact one
@ExtendWith(PactConsumerTestExt.class)  // Use Pact consumer extension
@PactTestFor(providerName = "spaceships-rest", port = "49635", pactVersion = PactSpecVersion.V3)  // Tell Pact where to run mock-provider
class SpaceshipsClientTest {

    // We define two states here we expect the provider could have
    private static final String STATE_HAS_SHIPS_ON_PLANET_1 = "has ships on planet 1";
    private static final String STATE_HAS_NO_SHIPS_ON_PLANET_2 = "has no ships on planet 2";

    @Pact(provider = "spaceships-rest", consumer = "planets-rest")
    RequestResponsePact pactFetchExistingSpaceshipsOnPlanet(PactDslWithProvider builder) {
        return builder
                .given(STATE_HAS_SHIPS_ON_PLANET_1)
                .uponReceiving("GET request to existing spaceships on planet 1")
                .path("/spaceships")
                .matchQuery("planetId", "\\d*", "1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Collections.singletonMap("Content-Type", "application/json"))
                .body(newJsonArrayMinLike(1, array -> array.object(spaceship -> {
                    spaceship.numberType("id", 123L);
                    spaceship.stringType("name", "X-Wing");
                })).build())
                .toPact();
    }

    @Pact(provider = "spaceships-rest", consumer = "planets-rest")
    RequestResponsePact pactFetchMissingSpaceshipsOnPlanet(PactDslWithProvider builder) {
        return builder
                .given(STATE_HAS_NO_SHIPS_ON_PLANET_2)
                .uponReceiving("GET request to non-existing spaceships on planet 2")
                .path("/spaceships")
                .matchQuery("planetId", "\\d*", "2")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Collections.singletonMap("Content-Type", "application/json"))
                .body(new PactDslJsonArray())
                .toPact();
    }

    @Autowired
    private SpaceshipsClient spaceshipsClient;

    @Test
    @PactTestFor(pactMethod = "pactFetchExistingSpaceshipsOnPlanet")
    void fetchExistingSpaceshipsOnPlanet() {
        List<Spaceship> spaceships = spaceshipsClient.getSpaceshipsOnPlanet(1);

        assertThat(spaceships.size()).isEqualTo(1);
        assertThat(spaceships.get(0).getId()).isEqualTo(123L);
        assertThat(spaceships.get(0).getName()).isEqualTo("X-Wing");
    }

    @Test
    @PactTestFor(pactMethod = "pactFetchMissingSpaceshipsOnPlanet")
    void fetchMissingSpaceshipsOnPlanet() {
        List<Spaceship> spaceships = spaceshipsClient.getSpaceshipsOnPlanet(2);

        assertThat(spaceships.size()).isEqualTo(0);
    }

}