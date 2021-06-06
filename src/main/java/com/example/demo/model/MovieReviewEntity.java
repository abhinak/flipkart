package com.example.demo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "movieReviews")
@Data
public class MovieReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @ManyToOne()
    @JoinColumn(name = "movieId")
    private MovieEntity movieEntity;

    private Integer review;

    @CreationTimestamp
    private Date creationTime;
    @UpdateTimestamp
    private Date modifiedTime;
}
