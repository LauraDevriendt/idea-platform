package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.Idea.comments.Comment;
import com.coaching.ideaplatform.Idea.comments.CommentDTO;
import com.coaching.ideaplatform.users.User;
import com.coaching.ideaplatform.users.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class IdeaDTO {
    private Long id;

    private String title;

    private  String description;

    private  Boolean publicIdea;

    @NotEmpty
    @JsonIgnoreProperties({"ideas"})
    private List<UserDTO> users = new ArrayList<>();
    @JsonIgnoreProperties({"idea"})
    private List<CommentDTO> comments = new ArrayList<>();

    public Idea toEntity(){
        Idea idea = new Idea();
        idea.setId(this.getId());
        idea.setTitle(this.getTitle());
        idea.setDescription(this.getDescription());
        List<User> users = this.getUsers().stream()
                .map(UserDTO::toChildEntity)
                .collect(Collectors.toList());
        idea.setUsers(users);
        idea.setPublicIdea(this.getPublicIdea());
        List<Comment> comments = this.getComments().stream()
                .map(CommentDTO::toEntity)
                .collect(Collectors.toList());
        idea.setComments(comments);

        return idea;
    }

    public Idea toChildEntity(){
        Idea idea = new Idea();
        idea.setId(this.getId());
        idea.setTitle(this.getTitle());
        idea.setDescription(this.getDescription());
        idea.setPublicIdea(this.getPublicIdea());
        List<Comment> comments = this.getComments().stream()
                .map(CommentDTO::toEntity)
                .collect(Collectors.toList());
        idea.setComments(comments);

        return idea;
    }
}
