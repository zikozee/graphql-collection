package com.zikozee.graphql.service.command;

import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.datasource.problemz.repository.ProblemzRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

@Service
@RequiredArgsConstructor
public class ProblemzCommandService {

    private Sinks.Many<Problemz> problemzSinks = Sinks.many().multicast().onBackpressureBuffer();

    private final ProblemzRepository problemzRepository;


    public Problemz createProblemz(Problemz p){
        var created = problemzRepository.save(p);

        problemzSinks.tryEmitNext(created);

        return created;
    }

    public Flux<Problemz> problemzFlux(){
        return problemzSinks.asFlux();
    }
}
