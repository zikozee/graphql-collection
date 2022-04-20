package com.zikozee.graphql.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Ezekiel Eromosei
 * @created: 20 April 2022
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphqlErrorResponse {

    private String message;
    private List<String> path;
    private List<Location> locations;


    @Getter
    @Setter
    public static class Location {
        private int line;
        private int column;
    }
}
