package com.example.demo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "movies")
@Data
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String genre;
    private Integer releaseYear;

    @CreationTimestamp
    private Date creationTime;
    @UpdateTimestamp
    private Date modifiedTime;
}
