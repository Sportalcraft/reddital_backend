package com.project.reddital_backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "posts")
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id; //PK


    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @CreationTimestamp
    //@Generated(GenerationTime.INSERT)
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time", /*columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",*/ insertable=false, updatable = false)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id")
    private SubReddit subreddit;

    @PrePersist
    protected void onCreate() {
        if (time == null) { time = new Date(); }
    }
}
