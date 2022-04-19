package com.zikozee.graphql.service.command;

import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.datasource.problemz.repository.ProblemzRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

@Service
@RequiredArgsConstructor
public class ProblemzCommandService {

    private final ProblemzRepository problemzRepository;


    public Problemz createProblemz(Problemz p){
        return problemzRepository.save(p);
    }
}
