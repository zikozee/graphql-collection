package com.zikozee.graphql.service.command;

import com.zikozee.graphql.datasource.problemz.entity.UserzToken;
import com.zikozee.graphql.datasource.problemz.repository.UserzRepository;
import com.zikozee.graphql.datasource.problemz.repository.UserzTokenRepository;
import com.zikozee.graphql.exception.ProblemzAuthenticationException;
import com.zikozee.graphql.util.HashUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

@Service
public class UserzCommandService {

    @Autowired
    private UserzRepository userzRepository;

    @Autowired
    private UserzTokenRepository userzTokenRepository;


    public UserzToken login(String username, String password){
        var userzQueryResult = userzRepository.findByUsernameIgnoreCase(username);

        if(userzQueryResult.isEmpty() || !HashUtil.isBcryptMatch(password, userzQueryResult.get().getHashedPassword())){
//            throw new IllegalArgumentException("Invalid credential");
            throw new ProblemzAuthenticationException();
        }

        var randomAuthToken = RandomStringUtils.randomAlphanumeric(40);

        return refreshToken(userzQueryResult.get().getId(), randomAuthToken);
    }


    private UserzToken refreshToken(UUID userId, String authToken){
        var userzToken = new UserzToken();
        userzToken.setUserId(userId);
        userzToken.setAuthToken(authToken);

        var now = LocalDateTime.now();
        userzToken.setCreateTimestamp(now);
        userzToken.setExpiryTimestamp(now.plusHours(2));

        return userzTokenRepository.save(userzToken);
    }
}
