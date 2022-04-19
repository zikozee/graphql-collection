package com.zikozee.graphql.service.query;

import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.datasource.problemz.entity.Solutionz;
import com.zikozee.graphql.datasource.problemz.repository.ProblemzRepository;
import com.zikozee.graphql.generated.types.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */

@Service
@RequiredArgsConstructor
public class ProblemzQueryService {

    private final ProblemzRepository problemzRepository;

    public List<Problemz> problemzLatestList(){
        var problemz = problemzRepository.findAllByOrderByCreationTimestampDesc();
//        problemz.forEach(p -> p.getSolutions().sort(Comparator.comparing(Solutionz::getCreationTimestamp).reversed()));
        return problemz;
    }

    public Optional<Problemz> problemzDetail(UUID problemzId){
        return problemzRepository.findById(problemzId);
    }

    public List<Problemz> problemzByKeyword(String keyword){
        return problemzRepository.findByKeyword("%" + keyword +"%");
    }
}
