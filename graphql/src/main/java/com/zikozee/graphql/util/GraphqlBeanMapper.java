package com.zikozee.graphql.util;

import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.datasource.problemz.entity.Solutionz;
import com.zikozee.graphql.datasource.problemz.entity.Userz;
import com.zikozee.graphql.datasource.problemz.entity.UserzToken;
import com.zikozee.graphql.generated.types.*;
import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */

public class GraphqlBeanMapper {

    private GraphqlBeanMapper(){}

    public static final PrettyTime PRETTY_TIME = new PrettyTime();

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(1);

    public static User mapToGraphql(Userz original){
        var createdDateTime  = original.getCreationTimestamp().atOffset(ZONE_OFFSET);

        return User.newBuilder()
                .avatar(original.getAvatar())
                .createdDateTime(createdDateTime)
                .displayName(original.getDisplayName())
                .email(original.getEmail())
                .id(original.getId().toString())
                .username(original.getUsername())
                .build();
    }

    public static Problem mapToGraphql(Problemz original){
        var creationDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);
        var author = mapToGraphql(original.getCreatedBy());
        var convertedSolutions = original.getSolutions().stream()
//                .sorted(Comparator.comparing(Solutionz::getCreationTimestamp).reversed())
                .map(GraphqlBeanMapper::mapToGraphql)
                .collect(Collectors.toList());

        var tagsList = List.of(original.getTags().split(","));

        return Problem.newBuilder()
                .author(author)
                .content(original.getContent())
                .createdDateTime(creationDateTime)
                .id(original.getId().toString())
                .solutions(convertedSolutions)
                .tags(tagsList)
                .title(original.getTitle())
                .solutionCount(convertedSolutions.size())
                .prettyCreatedDateTime(PRETTY_TIME.format(creationDateTime))
                .build();
    }

    public static Solution mapToGraphql(Solutionz original){

        var creationDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);
        var author = mapToGraphql(original.getCreatedBy());
        var category = StringUtils.equalsIgnoreCase(
                original.getCategory(), SolutionCategory.EXPLANATION.toString()) ?
                SolutionCategory.EXPLANATION : SolutionCategory.REFERENCE;

        return Solution.newBuilder()
                .author(author)
                .category(category)
                .content(original.getContent())
                .createdDateTime(creationDateTime)
                .id(original.getId().toString())
                .voteAsBadCount(original.getVoteBadCount())
                .voteAsGoodCount(original.getVoteGoodCount())
                .prettyCreatedDateTime(PRETTY_TIME.format(creationDateTime))
                .build();
    }

    public static UserAuthToken mapToGraphql(UserzToken original){
        var expiryDateTime = original.getExpiryTimestamp().atOffset(ZONE_OFFSET);


        return UserAuthToken.newBuilder()
                .authToken(original.getAuthToken())
                .expiryTime(expiryDateTime)
                .build();
    }

    public static Problemz mapToEntity(ProblemCreateInput original, Userz author){
        var result = new Problemz();

        result.setContent(original.getContent());
        result.setCreatedBy(author);
        result.setId(UUID.randomUUID());
        result.setSolutions(Collections.emptyList());
        result.setTags(String.join(",", original.getTags()));
        result.setTitle(original.getTitle());

        return result;
    }

    public static Solutionz mapToEntity(SolutionCreateInput original, Userz author, Problemz problemz){
        var result = new Solutionz();

        result.setCategory(original.getCategory().name());
        result.setContent(original.getContent());
        result.setCreatedBy(author);
        result.setProblemz(problemz);
        result.setId(UUID.randomUUID());

        return result;
    }

    public static Userz mapToEntity(UserCreateInput original){
        var result = new Userz();

        result.setId(UUID.randomUUID());
        result.setHashedPassword(HashUtil.hashBcrypt(original.getPassword()));
        result.setUsername(original.getUsername());
        result.setEmail(original.getEmail());
        result.setDisplayName(original.getDisplayName());
        result.setUserRole(original.getUserRole());
        result.setAvatar(original.getAvatar());
        result.setActive(true);

        return result;
    }
}
