package com.zikozee.graphql.exception.handler;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.exception.ProblemzAuthenticationException;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

@Component
public class ProblemsGraphqlExceptionHandler implements DataFetcherExceptionHandler {

    private final DefaultDataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();

    @Override
    public DataFetcherExceptionHandlerResult onException(DataFetcherExceptionHandlerParameters handlerParameters) {
        var exception = handlerParameters.getException();

        if(exception instanceof ProblemzAuthenticationException){
            var graphqlError = TypedGraphQLError.newBuilder()
                    .message(exception.getMessage())
                    .path(handlerParameters.getPath())
//                    .errorType(ErrorType.UNAUTHENTICATED) // returning enum
                    .errorDetail(new ProblemErrorDetail()) // returning custom message
                    .build();

            return DataFetcherExceptionHandlerResult.newResult().error(graphqlError).build();
        }
        return defaultHandler.onException(handlerParameters);
    }
}
