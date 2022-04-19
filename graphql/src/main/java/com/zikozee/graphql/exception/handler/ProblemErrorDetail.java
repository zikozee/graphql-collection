package com.zikozee.graphql.exception.handler;

import com.netflix.graphql.types.errors.ErrorDetail;
import com.netflix.graphql.types.errors.ErrorType;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

public class ProblemErrorDetail implements ErrorDetail {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.UNAUTHENTICATED;
    }

    @Override
    public String toString() {
        return "User validation failed. Check that username & password combination match " +
                "(both are case sensitive)";
    }
}
