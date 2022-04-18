package com.zikozee.graphql.datasource.fake;

import com.github.javafaker.Faker;
import com.zikozee.graphql.generated.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Ezekiel Eromosei
 * @created: 16 April 2022
 */

@Configuration
public class FakeStockDataSource {

    @Autowired
    private Faker faker;

    public Stock randomStock() {
        return Stock.newBuilder().lastTradeDateTime(OffsetDateTime.now())
                .price(faker.random().nextInt(100, 1000))
                .symbol(faker.stock().nyseSymbol())
                .build();
    }
}
