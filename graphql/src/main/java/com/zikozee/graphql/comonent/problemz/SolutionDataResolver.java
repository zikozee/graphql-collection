package com.zikozee.graphql.comonent.problemz;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.Solution;
import com.zikozee.graphql.generated.types.SolutionCreateInput;
import com.zikozee.graphql.generated.types.SolutionResponse;
import com.zikozee.graphql.generated.types.SolutionVoteInput;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */


@DgsComponent
public class SolutionDataResolver {

    @DgsMutation(field = DgsConstants.MUTATION.ProblemSolution)
    public SolutionResponse createSolution(@RequestHeader(name = "authToken") String authToken,
                                           @InputArgument(name = "newSolution") SolutionCreateInput solutionCreateInput) {
        return null;
    }

    @DgsMutation(field = DgsConstants.MUTATION.SolutionVote)
    public SolutionResponse createSolution(@RequestHeader(name = "authToken") String authToken,
                                           @InputArgument(name = "vote") SolutionVoteInput solutionVoteInput) {
        return null;
    }

    @DgsSubscription(field = DgsConstants.SUBSCRIPTION.SolutionVoteChanged)
    public Flux<Solution> createSolution(@InputArgument(name = "solutionId") String solutionId) {
        return null;
    }
}
