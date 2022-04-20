package com.zikozee.graphqlclient;

import com.apollographql.apollo.ApolloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class GraphqlStockClientTest {

    private Logger logger = LoggerFactory.getLogger(GraphqlStockClientTest.class);

    private final String serverEndpoint = "http://localhost:8080/graphql";
    private final String subscriptionEndpoint = "ws://localhost:8080/subscriptions";

    private GraphqlStockClient client = new GraphqlStockClient(serverEndpoint, subscriptionEndpoint);

    @Test
    void subscribeStock() throws Exception {

        client.subscribeStock();
        TimeUnit.SECONDS.sleep(15);

        assertTrue(client.shutdown());
    }
}