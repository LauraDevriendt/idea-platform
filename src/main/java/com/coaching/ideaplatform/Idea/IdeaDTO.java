package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.users.User;
import com.coaching.ideaplatform.users.UserDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class IdeaDTO {
    private Long id;

    private  String description;

    private  Boolean publicIdea;

    @NotEmpty
    private List<UserDTO> users = new ArrayList<>();

    public Idea toEntity(){
        Idea idea = new Idea();
        idea.setId(this.getId());
        idea.setDescription(this.getDescription());
        List<User> users = this.getUsers().stream()
                .map(UserDTO::toChildEntity)
                .collect(Collectors.toList());
        idea.setUsers(users);
        idea.setPublicIdea(this.getPublicIdea());

        return idea;
    }

    public Idea toChildEntity(){
        Idea idea = new Idea();
        idea.setId(this.getId());
        idea.setDescription(this.getDescription());
        idea.setPublicIdea(this.getPublicIdea());

        return idea;
    }


}
