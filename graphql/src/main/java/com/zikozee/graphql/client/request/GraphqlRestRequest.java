package com.zikozee.graphql.client.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

@Getter
@Setter
public class GraphqlRestRequest {

    private String query;

    private Map<String, ? extends Object> variables;
}
