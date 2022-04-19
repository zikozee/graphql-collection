package com.zikozee.graphql.exception;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

public class ProblemzAuthenticationException extends RuntimeException{

    public ProblemzAuthenticationException() {
        super("Invalid credential");
    }
}
