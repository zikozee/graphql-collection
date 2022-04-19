package com.zikozee.graphql.service.command;

import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.datasource.problemz.entity.Solutionz;
import com.zikozee.graphql.datasource.problemz.repository.ProblemzRepository;
import com.zikozee.graphql.datasource.problemz.repository.SolutionzRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

@Service
@RequiredArgsConstructor
public class SolutionzCommandService {

    private final SolutionzRepository solutionzRepository;


    public Solutionz createSolutionz(Solutionz p){
        return solutionzRepository.save(p);
    }


    public Optional<Solutionz> voteBad(UUID solutionzId){
        solutionzRepository.addVoteBadCount(solutionzId);

        return solutionzRepository.findById(solutionzId);
    }

    public Optional<Solutionz> voteGood(UUID solutionzId){
        solutionzRepository.addVoteGoodCount(solutionzId);

        return solutionzRepository.findById(solutionzId);
    }
}
