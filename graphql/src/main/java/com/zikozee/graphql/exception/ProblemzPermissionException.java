package com.zikozee.graphql.exception;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

public class ProblemzPermissionException extends RuntimeException{

    public ProblemzPermissionException() {
        super("You are not allowed to access this operation");
    }
}
