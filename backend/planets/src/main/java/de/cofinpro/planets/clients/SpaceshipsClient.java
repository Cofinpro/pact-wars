package de.cofinpro.planets.clients;

import de.cofinpro.planets.clients.models.Spaceship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class SpaceshipsClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spaceships.baseurl}")
    private String spaceshipsBaseUrl;

    public List<Spaceship> getSpaceshipsOnPlanet(long planetId) {
        UriComponents uri = UriComponentsBuilder
                .fromUriString(spaceshipsBaseUrl)
                .path("spaceships")
                .queryParam("planetId", planetId)
                .build();
        ResponseEntity<List<Spaceship>> response = restTemplate.exchange(
                uri.toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load spaceships with Status Code " + response.getStatusCodeValue());
        }
        return response.getBody();
    }

}
