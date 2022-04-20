package com.zikozee.graphqlclient;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.zikozee.graphqlclient.BooksByReleasedQuery.BooksByReleased;
import com.zikozee.graphqlclient.BooksQuery.Book;
import com.zikozee.graphqlclient.type.HelloInput;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author: Ezekiel Eromosei
 * @created: 20 April 2022
 */

public class GraphqlHelloClient {

    private final ApolloClient apolloClient;

    public GraphqlHelloClient(String serverEndpoint){
        var okHttpClient = new OkHttpClient();

        apolloClient = ApolloClient.builder().serverUrl(serverEndpoint)
                .okHttpClient(okHttpClient).build();
    }

    public CompletableFuture<AddHelloMutation.Data> addHello(HelloInput newHello) {
        var result = new CompletableFuture<AddHelloMutation.Data>();
        var mutation = apolloClient.mutate(new AddHelloMutation(newHello));

        mutation.enqueue(new ApolloCall.Callback<AddHelloMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<AddHelloMutation.Data> response) {
                result.complete(response.getData());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                throw e;
            }
        });

        return result;
    }

    public CompletableFuture<ReplaceHelloTextMutation.Data> helloReplacement(HelloInput newHello) {
        var result = new CompletableFuture<ReplaceHelloTextMutation.Data>();
        var mutation = apolloClient.mutate(new ReplaceHelloTextMutation(newHello));

        mutation.enqueue(new ApolloCall.Callback<ReplaceHelloTextMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<ReplaceHelloTextMutation.Data> response) {
                result.complete(response.getData());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                throw e;
            }
        });

        return result;
    }

    public CompletableFuture<DeleteHelloMutation.Data> deleteHello(int number) {
        var result = new CompletableFuture<DeleteHelloMutation.Data>();
        var mutation = apolloClient.mutate(new DeleteHelloMutation(number));

        mutation.enqueue(new ApolloCall.Callback<DeleteHelloMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<DeleteHelloMutation.Data> response) {
                result.complete(response.getData());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                throw e;
            }
        });

        return result;
    }
}
