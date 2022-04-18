package com.zikozee.graphql.comonent.problemz;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.*;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */


@DgsComponent
public class UserDataResolver {

    @DgsQuery(field = DgsConstants.QUERY.Me)
    public User accountInfo(@RequestHeader(name = "authToken") String authToken){
        return null;
    }

    @DgsMutation(field = DgsConstants.MUTATION.UserCreate)
    public UserResponse createUser(@InputArgument(name = "user")UserCreateInput userCreateInput){
        return null;
    }

    @DgsMutation(field = DgsConstants.MUTATION.UserLogin)
    public  UserResponse userLogin(@InputArgument(name = "user")UserLoginInput userLoginInput){
        return null;
    }

    @DgsMutation(field = DgsConstants.MUTATION.UserActivation)
    public UserActivationResponse userActivation(@InputArgument(name = "user") UserActivationInput userActivationInput){
        return null;
    }


}
