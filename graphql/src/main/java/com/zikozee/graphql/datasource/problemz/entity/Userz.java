package com.zikozee.graphql.datasource.problemz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */

@Entity
@Table(name= "userz")
@Getter
@Setter
public class Userz {

    @Id
    private UUID id;
    private String username;
    private String email;
    private String hashedPassword;
    private URL avatar;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    private String displayName;
    private boolean active;
}
