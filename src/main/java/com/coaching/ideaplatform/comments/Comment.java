package com.coaching.ideaplatform.comments;

import com.coaching.ideaplatform.Idea.Idea;
import com.coaching.ideaplatform.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
public
class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lob
    private String comment;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties({"comments", "ideas"})
    private User user;

    @ManyToOne(optional = false)
   @JsonBackReference
    private Idea idea;

}
