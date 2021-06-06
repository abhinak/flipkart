package com.example.demo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String type;

    private String emaidID;

    @CreationTimestamp
    private Date creationTime;
    @UpdateTimestamp
    private Date modifiedTime;
}


