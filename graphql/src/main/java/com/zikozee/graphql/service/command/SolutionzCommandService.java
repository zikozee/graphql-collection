package com.zikozee.graphql.service.command;

import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.datasource.problemz.entity.Solutionz;
import com.zikozee.graphql.datasource.problemz.repository.ProblemzRepository;
import com.zikozee.graphql.datasource.problemz.repository.SolutionzRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

@Service
@RequiredArgsConstructor
public class SolutionzCommandService {

    private Sinks.Many<Solutionz> solutionzSinks = Sinks.many().multicast().onBackpressureBuffer();
    private final SolutionzRepository solutionzRepository;


    public Solutionz createSolutionz(Solutionz p){
        return solutionzRepository.save(p);
    }


    public Optional<Solutionz> voteBad(UUID solutionzId){
        solutionzRepository.addVoteBadCount(solutionzId);

        var updated =  solutionzRepository.findById(solutionzId);

        if(updated.isPresent()){
            solutionzSinks.tryEmitNext(updated.get());
        }
        return updated;
    }

    public Optional<Solutionz> voteGood(UUID solutionzId){
        solutionzRepository.addVoteGoodCount(solutionzId);

        var updated =  solutionzRepository.findById(solutionzId);

        if(updated.isPresent()){
            solutionzSinks.tryEmitNext(updated.get());
        }
        return updated;
    }

    public Flux<Solutionz> solutionzFlux(){
        return solutionzSinks.asFlux();
    }
}
