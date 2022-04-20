package com.zikozee.graphql.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author: Ezekiel Eromosei
 * @created: 20 April 2022
 */

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetResponse {

    private String name;
    private List<String> climates;
    private List<String> terrains;

}
