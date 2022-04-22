package com.zikozee.graphql.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikozee.graphql.client.request.GraphqlRestRequest;
import com.zikozee.graphql.client.response.FilmResponse;
import com.zikozee.graphql.client.response.GraphqlErrorResponse;
import com.zikozee.graphql.client.response.PlanetResponse;
import com.zikozee.graphql.client.response.StarshipResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author: Ezekiel Eromosei
 * @created: 20 April 2022
 */

@Component
public class StarwarsRestClient {

    private static final String URL = "https://swapi-graphql.netlify.app/.netlify/functions/index";

    private RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    public String asJson(GraphqlRestRequest body, Map<String, List<String>> headersMap) {
        var requestHeaders = new HttpHeaders();

        if(headersMap != null){
            headersMap.forEach(requestHeaders::addAll);
        }

        var responseEntity = restTemplate
                .postForEntity(URL, new HttpEntity<>(body, requestHeaders), String.class);

        return responseEntity.getBody();
    }

    public List<PlanetResponse> allPlanets() throws JsonProcessingException {
        var query = "query allPlanets {\n" +
                "  allPlanets {\n" +
                "    planets {\n" +
                "      name\n" +
                "      climates\n" +
                "      terrains\n" +
                "    }\n" +
                "  }\n" +
                "}";

        var body = new GraphqlRestRequest();
        body.setQuery(query);

        var json = asJson(body, null);
        var jsonNode = objectMapper.readTree(json);
        var data = jsonNode.at("/data/allPlanets/planets");

        return objectMapper.readValue(data.toString(), new TypeReference<List<PlanetResponse>>() {});
    }


    public StarshipResponse oneStarshipFixed() throws JsonProcessingException {
        var query = "query oneStarshipFixed {\n" +
                "  starship(id: \"c3RhcnNoaXBzOjU=\") {\n" +
                "    model\n" +
                "    name\n" +
                "    manufacturers\n" +
                "  }\n" +
                "}";

        var body = new GraphqlRestRequest();
        body.setQuery(query);


        var json = asJson(body, null);
        var jsonNode = objectMapper.readTree(json);
        var data = jsonNode.at("/data/starship");

        return objectMapper.readValue(data.toString(), StarshipResponse.class);
    }

    public FilmResponse oneFilm(String filmId) throws JsonProcessingException {
//        var query = "query oneFilm($filmId: ID!) {\n" +
//                "  film(filmID: $filmId) {\n" +
//                "    title\n" +
//                "    director\n" +
//                "    releaseDate\n" +
//                "  }\n" +
//                "}";
        var query = "query oneFilm($filmId:ID!){film(filmID:$filmId){title director releaseDate}}";

        var body = new GraphqlRestRequest();
        body.setQuery(query);

        var variableMap = Map.of("filmId", filmId);
        body.setVariables(variableMap);

        var json = asJson(body, null);
        var jsonNode = objectMapper.readTree(json);
        var data = jsonNode.at("/data/film");

        return objectMapper.readValue(data.toString(), FilmResponse.class);
    }

    public List<GraphqlErrorResponse> onFilmInvalid() throws JsonProcessingException {

        var query = "query oneFilm($filmId: ID!) {\n" +
                "  film(filmID: $filmId) {\n" +
                "    title\n" +
                "    director\n" +
                "    releaseDate\n" +
                "  }\n" +
                "}";

        var body = new GraphqlRestRequest();
        body.setQuery(query);

        var variableMap = Map.of("filmId", "******");
        body.setVariables(variableMap);

        var json = asJson(body, null);
        var jsonNode = objectMapper.readTree(json);
        var errors = jsonNode.at("/errors");

        if(errors != null) {
            return objectMapper.readValue(errors.toString(), new TypeReference<List<GraphqlErrorResponse>>() {});
        }

        return  null;
    }
}
