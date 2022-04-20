package com.zikozee.graphqlclient;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.zikozee.graphqlclient.BooksQuery.Book;
import com.zikozee.graphqlclient.BooksByReleasedQuery.BooksByReleased;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author: Ezekiel Eromosei
 * @created: 20 April 2022
 */

public class GraphqlBookClient {

    private final ApolloClient apolloClient;

    public GraphqlBookClient(String serverEndpoint){
        var okHttpClient = new OkHttpClient();

        apolloClient = ApolloClient.builder().serverUrl(serverEndpoint)
                .okHttpClient(okHttpClient).build();
    }

    public CompletableFuture<List<Book>> allBooks(){
        var result = new CompletableFuture<List<Book>>();
        var query = apolloClient.query(new BooksQuery());

        query.enqueue(new ApolloCall.Callback<BooksQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<BooksQuery.Data> response) {
                result.complete(response.getData().books);
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                throw e;
            }
        });

        return result;
    }

    public CompletableFuture<List<BooksByReleased>> booksByReleased(int releaseYear, boolean releasePrintedEdition){
        var result = new CompletableFuture<List<BooksByReleased>>();
        var query = apolloClient
                .query(new BooksByReleasedQuery(Input.fromNullable(releaseYear), (Input.fromNullable(releasePrintedEdition))));

        query.enqueue(new ApolloCall.Callback<BooksByReleasedQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<BooksByReleasedQuery.Data> response) {
                result.complete(response.getData().booksByReleased());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                throw e;
            }
        });

        return result;
    }
}
