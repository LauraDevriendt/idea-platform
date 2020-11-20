package com.coaching.ideaplatform.Idea.comments;

import com.coaching.ideaplatform.Idea.IdeaDTO;
import com.coaching.ideaplatform.users.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

    private String comment;

    @JsonIgnoreProperties("ideas")
    private UserDTO user;
    @JsonIgnoreProperties({"comments", "users"})
    private IdeaDTO idea;

    public Comment toEntity(){
        Comment comment = new Comment();
        comment.setId(this.getId());
        comment.setComment(this.getComment());
        comment.setUser(this.getUser().toEntity());
        comment.setIdea(this.getIdea().toEntity());
        return comment;
    }
}
