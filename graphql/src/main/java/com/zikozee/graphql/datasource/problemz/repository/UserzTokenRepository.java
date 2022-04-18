package com.zikozee.graphql.datasource.problemz.repository;

import com.zikozee.graphql.datasource.problemz.entity.Userz;
import com.zikozee.graphql.datasource.problemz.entity.UserzToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */

@Repository
public interface UserzTokenRepository extends CrudRepository<UserzToken, UUID> {
}
