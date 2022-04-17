package com.zikozee.graphql.datasource.fake;

import com.github.javafaker.Faker;
import com.zikozee.graphql.generated.types.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ezekiel Eromosei
 * @created: 16 April 2022
 */

@Configuration
public class FakeHelloDataSource {

    @Autowired
    private Faker faker;

    public static final List<Hello> HELLO_LIST = new ArrayList<>();

    @PostConstruct
    private void postCentral(){
        for (int i = 0; i < 20; i++) {
            var hello = Hello.newBuilder()
                    .randomNumber(faker.random().nextInt(5000))
                    .text(faker.company().name())
                    .build();

            HELLO_LIST.add(hello);
        }
    }
}
