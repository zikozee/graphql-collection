package com.zikozee.graphql;

import com.zikozee.graphql.client.StarwarsGraphqlClient;
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
class StarwarsGraphqlClientTests {
    private Logger logger = LoggerFactory.getLogger(StarwarsGraphqlClientTests.class);

    @Autowired
    private StarwarsGraphqlClient client;

    @Test
    void testAsJson() throws Exception {
        var result = client.asJson("allPlanets", null, null);

        assertNotNull(result);
    }

    @DisplayName(value = "invalid query test")
    @Test
    void testAsJsonInvalid() throws Exception {
        assertThrows(RuntimeException.class, () -> client.asJson("allPlanetsxxx", null, null));
    }

    @Test
    void testAllPlanets() throws Exception {
        var result = client.allPlanets();
        logger.info("data =============> {}", result);
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
        assertNotNull(result);
        assertNotNull(result.getTitle());
    }

    @Test
    void testOneFilm_Invalid() throws Exception {
        var errors = client.oneFilmInValid();
        assertNotNull(errors);
        assertFalse(errors.isEmpty());
    }
}
