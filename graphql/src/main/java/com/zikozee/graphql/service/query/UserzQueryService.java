package com.zikozee.graphql.service.query;

import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.datasource.problemz.entity.Userz;
import com.zikozee.graphql.datasource.problemz.repository.ProblemzRepository;
import com.zikozee.graphql.datasource.problemz.repository.UserzRepository;
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
public class UserzQueryService {

    private final UserzRepository userzRepository;

    public Optional<Userz> findByAuthToken(String authToken){
        return userzRepository.findUserByToken(authToken);
    }
}
