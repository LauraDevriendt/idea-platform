package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.comments.Comment;
import com.coaching.ideaplatform.users.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ideas")
@Data
public
class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String title;

    @Column(nullable = false)
    @Lob
    private  String description;

    @Column(nullable = false)
    private  Boolean publicIdea;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties({"ideas", "comments"})
    private User user;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Long id) {
        this.comments.remove(id);
    }
}
