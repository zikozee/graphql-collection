package com.zikozee.graphql.comonent.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.zikozee.graphql.datasource.fake.FakeHelloDataSource;
import com.zikozee.graphql.generated.types.Hello;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Ezekiel Eromosei
 * @created: 16 April 2022
 */

@DgsComponent
public class FakeHelloDataResolver {

    @DgsQuery
    public List<Hello> allHellos(){
        return FakeHelloDataSource.HELLO_LIST;
    }

    @DgsQuery
    public Hello oneHello(){
        return FakeHelloDataSource.HELLO_LIST.get(
                ThreadLocalRandom.current().nextInt(FakeHelloDataSource.HELLO_LIST.size())
        );
    }
}
