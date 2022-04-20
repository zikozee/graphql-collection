package com.zikozee.graphqlclient;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GraphqlBookClientTest {
    private Logger logger = LoggerFactory.getLogger(GraphqlBookClientTest.class);

    private final String serverEndpoint = "http://localhost:8080/graphql";

    private GraphqlBookClient client = new GraphqlBookClient(serverEndpoint);

    @Test
    void allBooks() throws Exception {
        var books = client.allBooks().get();

        assertNotNull(books);
        assertFalse(books.isEmpty());
        logger.info("allBooks: " + books);
    }

    @Test
    void booksByReleased() throws Exception {
        var books = client.booksByReleased(2020, true).get();

        assertNotNull(books);
        assertFalse(books.isEmpty());
        logger.info("booksByReleased: " + books);
    }
}