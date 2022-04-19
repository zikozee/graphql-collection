package com.zikozee.graphql.comonent.problemz;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.zikozee.graphql.datasource.problemz.entity.Solutionz;
import com.zikozee.graphql.exception.ProblemzAuthenticationException;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.Solution;
import com.zikozee.graphql.generated.types.SolutionCreateInput;
import com.zikozee.graphql.generated.types.SolutionResponse;
import com.zikozee.graphql.generated.types.SolutionVoteInput;
import com.zikozee.graphql.service.command.SolutionzCommandService;
import com.zikozee.graphql.service.query.ProblemzQueryService;
import com.zikozee.graphql.service.query.UserzQueryService;
import com.zikozee.graphql.util.GraphqlBeanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.nio.file.ProviderMismatchException;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */


@DgsComponent
@RequiredArgsConstructor
public class SolutionDataResolver {

    private final UserzQueryService userzQueryService;
    private final ProblemzQueryService problemzQueryService;
    private final SolutionzCommandService solutionzCommandService;


    @DgsMutation(field = DgsConstants.MUTATION.SolutionCreate)
    public SolutionResponse createSolution(@RequestHeader(name = "authToken") String authToken,
                                           @InputArgument(name = "newSolution") SolutionCreateInput solutionCreateInput) {
       var userz = userzQueryService.findByAuthToken(authToken)
               .orElseThrow(ProviderMismatchException::new);

       var problemId = UUID.fromString(solutionCreateInput.getProblemId());
       var problemz = problemzQueryService.problemzDetail(problemId)
               .orElseThrow(DgsEntityNotFoundException::new);

       var solutionz = GraphqlBeanMapper.mapToEntity(solutionCreateInput, userz, problemz);
       var created = solutionzCommandService.createSolutionz(solutionz);

       return SolutionResponse.newBuilder().solution(GraphqlBeanMapper.mapToGraphql(created)).build();

    }

    @DgsMutation(field = DgsConstants.MUTATION.SolutionVote)
    public SolutionResponse createSolutionVote(@RequestHeader(name = "authToken") String authToken,
                                           @InputArgument(name = "vote") SolutionVoteInput solutionVoteInput) {

        Optional<Solutionz> updated;
        userzQueryService.findByAuthToken(authToken)
                .orElseThrow(ProblemzAuthenticationException::new);

        if(solutionVoteInput.getVoteAsGood()){
            updated = solutionzCommandService.voteGood(UUID.fromString(solutionVoteInput.getSolutionId()));
        }else {
            updated = solutionzCommandService.voteBad(UUID.fromString(solutionVoteInput.getSolutionId()));
        }

        if(updated.isEmpty()) throw new DgsEntityNotFoundException("Solution " + solutionVoteInput.getSolutionId() + " not found");

        return SolutionResponse.newBuilder().solution(GraphqlBeanMapper.mapToGraphql(updated.get())).build();
    }

    @DgsSubscription(field = DgsConstants.SUBSCRIPTION.SolutionVoteChanged)
    public Flux<Solution> subscribeSolutionVote(@InputArgument(name = "solutionId") String solutionId) {
        return solutionzCommandService.solutionzFlux()
                .map(GraphqlBeanMapper::mapToGraphql);
    }
}
