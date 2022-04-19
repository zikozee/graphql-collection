package com.zikozee.graphql.comonent.problemz;

import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.Problem;
import com.zikozee.graphql.generated.types.ProblemCreateInput;
import com.zikozee.graphql.generated.types.ProblemResponse;
import com.zikozee.graphql.service.query.ProblemzQueryService;
import com.zikozee.graphql.util.GraphqlBeanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */


@DgsComponent
@RequiredArgsConstructor
public class ProblemDataResolver {

    private final ProblemzQueryService queryService;

    @DgsQuery(field = DgsConstants.QUERY.ProblemLatestList)
    public List<Problem> getProblemLatestList(){

        return queryService.problemzLatestList().stream()
                .map(GraphqlBeanMapper::mapToGraphql)
                .collect(Collectors.toList());

    }

    @DgsQuery(field = DgsConstants.QUERY.ProblemDetail)
    public Problem problemDetail(@InputArgument(name = "id") String id){
        var problemId = UUID.fromString(id);
        var problemz = queryService.problemzDetail(problemId).orElseThrow(DgsBadRequestException::new);
        return GraphqlBeanMapper.mapToGraphql(problemz);
    }

    @DgsMutation(field = DgsConstants.MUTATION.ProblemCreate)
    public ProblemResponse createProblem(@RequestHeader(name = "authToken") String authToken,
                                         @InputArgument(name = "problem")ProblemCreateInput problemCreateInput){
        return null;
    }

    @DgsSubscription(field = DgsConstants.SUBSCRIPTION.ProblemAdded)
    public Flux<Problem> subscribeProblemAdded(){
        return null;
    }
}
