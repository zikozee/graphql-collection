package com.zikozee.graphql.client;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.*;
import com.zikozee.graphql.client.response.FilmResponse;
import com.zikozee.graphql.client.response.PlanetResponse;
import com.zikozee.graphql.client.response.StarshipResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Ezekiel Eromosei
 * @created: 20 April 2022
 */

@Component
public class StarwarsGraphqlClient {

    private static final String QUERY = "query allPlanets {\n" +
            "  allPlanets {\n" +
            "    planets {\n" +
            "      name\n" +
            "      climates\n" +
            "      terrains\n" +
            "    }\n" +
            "  }\n" +
            "}\n" +
            "query oneStarshipFixed {\n" +
            "  starship(id: \"c3RhcnNoaXBzOjU=\") {\n" +
            "    model\n" +
            "    name\n" +
            "    manufacturers\n" +
            "  }\n" +
            "}\n" +
            "query oneFilm($filmId: ID!) {\n" +
            "  film(filmID: $filmId) {\n" +
            "    title\n" +
            "    director\n" +
            "    releaseDate\n" +
            "  }\n" +
            "}\n";

    private static final String URL = "https://swapi-graphql.netlify.app/.netlify/functions/index";

    private GraphQLClient graphQLClient = new DefaultGraphQLClient(URL);

    private RestTemplate restTemplate = new RestTemplate();

    private GraphQLResponse getGraphQlResponse(String operationName, Map<String, ? extends Object> variablesMap,
                                               Map<String, List<String>> headersMap){

        if(variablesMap == null){
            variablesMap = new HashMap<>();
        }

        return graphQLClient.executeQuery(QUERY, variablesMap, operationName,
                (url, headers, body) -> {

            var requestHeaders = new HttpHeaders();
                    requestHeaders.putAll(headers);

                    if(headersMap != null){
                        headersMap.forEach(requestHeaders::addAll);
                    }

                    var responseEntity = restTemplate.postForEntity(url,
                            new HttpEntity<>(body, requestHeaders), String.class);

                    return new HttpResponse(responseEntity.getStatusCodeValue(), responseEntity.getBody());
            });
    }


    public String asJson(String operationName, Map<String, Object> variablesMap, Map<String, List<String>> headersMap){
        return getGraphQlResponse(operationName, variablesMap, headersMap).getJson();
    }

    public List<PlanetResponse> allPlanets(){
        return getGraphQlResponse("allPlanets", null, null)
                .extractValueAsObject("allPlanets.planets", new TypeRef<List<PlanetResponse>>() {}); //TODO INFO: NOTE THIS NOT TypeReference
    }

    public StarshipResponse oneStarshipFixed(){
        return getGraphQlResponse("oneStarshipFixed", null, null)
                .extractValueAsObject("starship", StarshipResponse.class);
    }

    public FilmResponse oneFilm(String filmId){
        var variablesMap = Map.of("filmId", filmId);
        return getGraphQlResponse("oneFilm", variablesMap, null)
                .extractValueAsObject("film", FilmResponse.class);
    }

    public List<GraphQLError> oneFilmInValid(){
        var variablesMap = Map.of("filmId", "xxxxxxx");
        var graphqlResponse = getGraphQlResponse("oneFilm", variablesMap, null);

        //todo info we can utilize, if errors, throw, else return ResponseObject
        // that way we can have both error and object
        return graphqlResponse.hasErrors() ? graphqlResponse.getErrors() : null;
    }
}
