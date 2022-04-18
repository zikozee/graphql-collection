package com.zikozee.graphql.datasource.problemz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */

@Entity
@Table(name= "solutionz")
@Getter
@Setter
public class Solutionz {

    @Id
    private UUID id;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    private String content;
    private String category;
    private int voteGoodCount;
    private int voteBadCount;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Userz createdBy;

    @ManyToOne
    @JoinColumn(name= "problemz_id", nullable = false)
    private Problemz problemz;
}
