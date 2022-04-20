package com.zikozee.graphqlclient;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloSubscriptionCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Ezekiel Eromosei
 * @created: 20 April 2022
 */

public class GraphqlStockClient {

    private Logger logger = LoggerFactory.getLogger(GraphqlStockClient.class);

    private final ApolloClient apolloClient;

    public GraphqlStockClient(String serverEndpoint, String subscriptionEndpoint){
        var okHttpClient = new OkHttpClient();

        apolloClient = ApolloClient.builder()
                .serverUrl(serverEndpoint)
                .subscriptionTransportFactory(new WebSocketSubscriptionTransport.Factory(subscriptionEndpoint, okHttpClient))
                .okHttpClient(okHttpClient)
                .build();

    }

    public void subscribeStock(){
        var subscription = apolloClient.subscribe(new GetStockEveryIntervalSubscription());

        subscription.execute(new ApolloSubscriptionCall.Callback<GetStockEveryIntervalSubscription.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetStockEveryIntervalSubscription.Data> response) {
                logger.info("Receiving stock " + response.getData().randomStock().symbol() + " with price "
                 + response.getData().randomStock().price());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
               throw e;
            }

            @Override
            public void onCompleted() {
                logger.info("onCompleted");
            }

            @Override
            public void onTerminated() {
                logger.info("onTerminated");
            }

            @Override
            public void onConnected() {
                logger.info("onConnected");
            }
        });
    }

    public boolean shutdown(){
        apolloClient.disableSubscriptions();
        return true;
    }
}
