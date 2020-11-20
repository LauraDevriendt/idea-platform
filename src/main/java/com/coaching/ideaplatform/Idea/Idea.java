package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.Idea.comments.Comment;
import com.coaching.ideaplatform.Idea.comments.CommentDTO;
import com.coaching.ideaplatform.users.User;
import com.coaching.ideaplatform.users.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @JsonIgnoreProperties(value = {"idea"}, allowSetters = true)
    private List<Comment> comments;

    public void removeUser(User user){
        this.users.remove(user);
    }

    public void removeComment(Comment comment){
        this.comments.remove(comment);
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void addUser(User user) {
        boolean userAlreadyInList = users.stream().anyMatch(userInData -> userInData.getId().equals(user.getId()));
        if(!userAlreadyInList){
            users.add(user);
        }
    }

    public IdeaDTO toDto(){
        IdeaDTO ideaDTO = new IdeaDTO();
        ideaDTO.setId(this.getId());
        ideaDTO.setTitle(this.getTitle());
        ideaDTO.setDescription(this.getDescription());
        ideaDTO.setPublicIdea(this.getPublicIdea());
        List<UserDTO> users = this.getUsers().stream().map(User::toChildDto).collect(Collectors.toList());
        ideaDTO.setUsers(users);
        List<CommentDTO> comments = this.getComments().stream().map(Comment::toDto).collect(Collectors.toList());
        ideaDTO.setComments(comments);
        return ideaDTO;
    }

    public IdeaDTO toChildDto(){
        IdeaDTO ideaDTO = new IdeaDTO();
        ideaDTO.setId(this.getId());
        ideaDTO.setTitle(this.getTitle());
        ideaDTO.setDescription(this.getDescription());
        ideaDTO.setPublicIdea(this.getPublicIdea());
        return ideaDTO;
    }
}
