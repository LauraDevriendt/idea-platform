package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.Idea.comments.Comment;
import com.coaching.ideaplatform.users.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.PERSIST})
    @JsonIgnoreProperties({"ideas", "comments"})
    private List<User> users = new ArrayList<>();

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
        boolean userAlreadyInList = users.stream().anyMatch(userInData -> userInData.getId().equals(user.getId()));
        if(!userAlreadyInList){
            users.add(user);
        }
    }
}
