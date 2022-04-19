package com.zikozee.graphql.datasource.problemz.repository;

import com.zikozee.graphql.datasource.problemz.entity.Solutionz;
import com.zikozee.graphql.datasource.problemz.entity.Userz;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */

@Repository
public interface SolutionzRepository extends CrudRepository<Solutionz, UUID> {

    @Query(nativeQuery = true, value = "select * from solutionz  z where upper(z.content) like upper(:keyword)")
    List<Solutionz> findByKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update solutionz set vote_bad_count = vote_bad_count + 1 where id = :solutionzId")
    void addVoteBadCount(@Param("solutionzId") UUID id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update solutionz set vote_good_count = vote_good_count + 1 where id = :solutionzId")
    void addVoteGoodCount(@Param("solutionzId") UUID id);
}
