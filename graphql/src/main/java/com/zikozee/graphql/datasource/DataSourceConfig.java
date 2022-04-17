package com.zikozee.graphql.datasource;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Ezekiel Eromosei
 * @created: 16 April 2022
 */

@Configuration
public class DataSourceConfig {

    @Bean
    public Faker faker(){
      return new Faker();
    }
}
