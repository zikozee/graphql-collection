package com.zikozee.graphql.service.query;

import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.datasource.problemz.entity.Solutionz;
import com.zikozee.graphql.datasource.problemz.repository.ProblemzRepository;
import com.zikozee.graphql.datasource.problemz.repository.SolutionzRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */

@Service
@RequiredArgsConstructor
public class SolutionzQueryService {

    private final SolutionzRepository solutionzRepository;


    public List<Solutionz> solutionzByKeyword(String keyword){
        return solutionzRepository.findByKeyword("%" + keyword +"%");
    }
}
