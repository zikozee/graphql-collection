package com.zikozee.graphql.comonent.problemz;

import com.netflix.graphql.dgs.*;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.Problem;
import com.zikozee.graphql.generated.types.ProblemCreateInput;
import com.zikozee.graphql.generated.types.ProblemResponse;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */


@DgsComponent
public class ProblemDataResolver {

    @DgsQuery(field = DgsConstants.QUERY.ProblemLatestList)
    public List<Problem> getProblemLatestList(){
        return null;
    }

    @DgsQuery(field = DgsConstants.QUERY.ProblemDetail)
    public Problem problemDetail(@InputArgument(name = "id") String id){
        return null;
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
