package com.zikozee.graphql;

import com.zikozee.graphql.client.StarwarsRestClient;
import com.zikozee.graphql.client.request.GraphqlRestRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static graphql.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class StarwarsRestClientTests {
    private Logger logger = LoggerFactory.getLogger(StarwarsRestClientTests.class);

    @Autowired
    private StarwarsRestClient client;

    @Test
    void testAsJson() throws Exception {
        var query = "query allPlanets {\n" +
                "  allPlanets {\n" +
                "    planets {\n" +
                "      name\n" +
                "      climates\n" +
                "      terrains\n" +
                "    }\n" +
                "  }\n" +
                "}";

        var body = new GraphqlRestRequest();
        body.setQuery(query);

        var result = client.asJson(body, null);

        assertNotNull(result);
    }

    @DisplayName(value = "invalid query test")
    @Test
    void testAsJsonInvalid() throws Exception {
        var query = "query allPlanets {\n" +
                "  allPlanetsxxxxxxxxx {\n" +
                "    planets {\n" +
                "      name\n" +
                "      climates\n" +
                "      terrains\n" +
                "    }\n" +
                "  }\n" +
                "}";

        var body = new GraphqlRestRequest();
        body.setQuery(query);

        assertThrows(RuntimeException.class, () -> client.asJson(body, null));
    }

    @Test
    void testAllPlanets() throws Exception {
        var result = client.allPlanets();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testOneStarshipFixed() throws Exception {
        var result = client.oneStarshipFixed();
        assertNotNull(result);
        assertNotNull(result.getModel());
        assertNotNull(result.getName());
        assertNotNull(result.getManufacturers());
    }

    @Test
    void testOneFilm_Right() throws Exception {
        var result = client.oneFilm("1");
        logger.info("RESULT {}", result);
        assertNotNull(result);
        assertNotNull(result.getTitle());
    }

    @Test
    void testOneFilm_Invalid() throws Exception {
        var errors = client.onFilmInvalid();
        assertNotNull(errors);
        assertFalse(errors.isEmpty());
    }
}
