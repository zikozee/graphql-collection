package com.zikozee.graphql.datasource.problemz.repository;

import com.zikozee.graphql.datasource.problemz.entity.Problemz;
import com.zikozee.graphql.datasource.problemz.entity.Userz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */

@Repository
public interface ProblemzRepository extends CrudRepository<Problemz, UUID> {

    List<Problemz> findAllByOrderByCreationTimestampDesc();

    @Query(nativeQuery = true, value = "select * from problemz p"
            + " where upper(p.content) like upper(:keyword) "
            + " or upper(p.title) like upper(:keyword) "
            + " or upper(p.tags) like upper(:keyword)")
    List<Problemz> findByKeyword(@Param("keyword") String keyword);
}
