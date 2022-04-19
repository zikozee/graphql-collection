package com.zikozee.graphql.comonent.problemz;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.zikozee.graphql.datasource.problemz.entity.Userz;
import com.zikozee.graphql.exception.ProblemzAuthenticationException;
import com.zikozee.graphql.exception.ProblemzPermissionException;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.*;
import com.zikozee.graphql.service.command.UserzCommandService;
import com.zikozee.graphql.service.query.UserzQueryService;
import com.zikozee.graphql.util.GraphqlBeanMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */


@DgsComponent
@RequiredArgsConstructor
public class UserDataResolver {

    private final UserzCommandService userzCommandService;
    private final UserzQueryService userzQueryService;

    @DgsQuery(field = DgsConstants.QUERY.Me)
    public User accountInfo(@RequestHeader(name = "authToken") String authToken){
        var userz = userzQueryService.findByAuthToken(authToken).orElseThrow(DgsEntityNotFoundException::new);
        return GraphqlBeanMapper.mapToGraphql(userz);
    }

    @Secured(value = "ROLE_ADMIN")
    @DgsMutation(field = DgsConstants.MUTATION.UserCreate)
    public UserResponse createUser(
            @InputArgument(name = "user")UserCreateInput userCreateInput){
//            @RequestHeader(name = "authToken") String authToken){

//        var userAuth = userzQueryService.findByAuthToken(authToken)
//                .orElseThrow(ProblemzAuthenticationException::new);
//
//        if(!StringUtils.equals(userAuth.getUserRole(), "ROLE_ADMIN")) throw new ProblemzPermissionException();

        var userz = GraphqlBeanMapper.mapToEntity(userCreateInput);
        var saved = userzCommandService.createUserz(userz);

       return UserResponse.newBuilder().user(GraphqlBeanMapper.mapToGraphql(saved)).build();
    }

    @DgsMutation(field = DgsConstants.MUTATION.UserLogin)
    public  UserResponse userLogin(@InputArgument(name = "user")UserLoginInput userLoginInput){
        var generatedToken = userzCommandService.login(userLoginInput.getUsername(), userLoginInput.getPassword());
        var userAuthToken = GraphqlBeanMapper.mapToGraphql(generatedToken);

        var userInfo = accountInfo(userAuthToken.getAuthToken());
        return UserResponse.newBuilder().authToken(userAuthToken).user(userInfo).build();
    }

    @Secured(value = "ROLE_ADMIN")
    @DgsMutation(field = DgsConstants.MUTATION.UserActivation)
    public UserActivationResponse userActivation(
            @InputArgument(name = "user") UserActivationInput userActivationInput){
//            @RequestHeader(name = "authToken") String authToken){

//        var userAuth = userzQueryService.findByAuthToken(authToken)
//                .orElseThrow(ProblemzAuthenticationException::new);
//
//
//        if(!StringUtils.equals(userAuth.getUserRole(), "ROLE_ADMIN")) throw new ProblemzPermissionException();

        var updated = userzCommandService
                .activateUser(userActivationInput.getUsername(), userActivationInput.getActive())
                .orElseThrow(DgsEntityNotFoundException::new);

        return UserActivationResponse.newBuilder().isActive(updated.isActive()).build();
    }


}
