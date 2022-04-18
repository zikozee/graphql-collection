package com.zikozee.graphql.datasource.problemz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */


@Entity
@Table(name= "problemz")
@Getter
@Setter
public class Problemz {

    @Id
    private UUID id;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    private String title;
    private String content;
    private String tags;

    @OneToMany(mappedBy = "problemz")
    private List<Solutionz> solutions;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Userz createdBy;
}
