package com.zikozee.graphql.comonent.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsSubscription;
import com.zikozee.graphql.datasource.fake.FakeStockDataSource;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.Stock;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.Flow;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */

@DgsComponent
public class FakeStockDataResolver {

    @Autowired
    private FakeStockDataSource dataSource;

//    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE, field = DgsConstants.SUBSCRIPTION.RandomStock)
    @DgsSubscription(field = DgsConstants.SUBSCRIPTION.RandomStock)
    public Publisher<Stock> randomStock(){

        return Flux.interval(Duration.ofSeconds(3))
                .map(t -> dataSource.randomStock());
    }
}

