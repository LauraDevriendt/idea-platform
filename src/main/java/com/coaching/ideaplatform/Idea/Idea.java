package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.comments.Comment;
import com.coaching.ideaplatform.users.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "ideas")
@Getter
@Setter
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

    //@todo cascade hier weg of het loopt verkeerd ...
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.PERSIST})
    @NotEmpty
    @JsonIgnoreProperties({"ideas", "comments"})
    private List<User> users;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"ideas"}, allowSetters = true)
    private List<Comment> comments;

    public void removeUser(User user){
        this.users.remove(user);
    }

    public void removeComment(Comment comment){
        this.comments.remove(comment);
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
