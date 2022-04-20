package com.zikozee.graphqlclient;

import com.zikozee.graphqlclient.type.HelloInput;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class GraphqlHelloClientTest {
    private Logger logger = LoggerFactory.getLogger(GraphqlHelloClientTest.class);

    private final String serverEndpoint = "http://localhost:8080/graphql";

    private GraphqlHelloClient client = new GraphqlHelloClient(serverEndpoint);


    @Test
    void addHello() throws Exception {

        var newHello = HelloInput.builder().number(ThreadLocalRandom.current().nextInt()).text("New Hello")
                .build();

        var mutationResult = client.addHello(newHello).get();

        assertNotNull(mutationResult);
        logger.info("addHello: " + mutationResult);
    }

    @Test
    void helloReplacement()  throws Exception {
        var replacementHello = HelloInput.builder().text("Replacement Hello").number(4458).build();

        var mutationResult = client.helloReplacement(replacementHello).get();

        assertNotNull(mutationResult);
        logger.info("helloReplacement: " + mutationResult);
    }

    @Test
    void deleteHello() throws Exception {

        var mutationResult = client.deleteHello(3259).get();

        assertNotNull(mutationResult);
        logger.info("deleteHello: " + mutationResult);
    }
}