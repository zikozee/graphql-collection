package com.zikozee.graphql.datasource.fake;

import com.github.javafaker.Faker;
import com.zikozee.graphql.generated.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.zikozee.graphql.generated.types.Dog.newBuilder;

/**
 * @author: Ezekiel Eromosei
 * @created: 16 April 2022
 */

@Configuration
public class FakePetDataSource {

    @Autowired
    private Faker faker;
    public static final List<Pet> PET_LIST = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 10; i++) {
            Pet animal;
            if (i % 2 == 0) {
                animal = newBuilder().name(faker.dog().name())
                        .food(PetFoodType.OMNIVORE)
                        .breed(faker.dog().breed())
                        .size(faker.dog().size())
                        .coatLength(faker.dog().coatLength())
                        .build();
            }else {
                animal= Cat.newBuilder().name(faker.cat().name())
                        .food(PetFoodType.CARNIVORE)
                        .breed(faker.cat().breed())
                        .registry(faker.cat().registry())
                        .build();
            }

            PET_LIST.add(animal);
        }
    }
}
