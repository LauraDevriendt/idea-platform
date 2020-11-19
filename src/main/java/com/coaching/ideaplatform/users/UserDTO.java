package com.coaching.ideaplatform.users;

import com.coaching.ideaplatform.Idea.Idea;
import com.coaching.ideaplatform.Idea.IdeaDTO;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {

    private Long id;

    private String username;

    private List<IdeaDTO> ideas = new ArrayList<>();

    public User toEntity(){
        User user = new User();
        user.setId(this.getId());
        user.setUsername(this.getUsername());
        List<Idea> ideas = this.getIdeas().stream()
                .map(IdeaDTO::toChildEntity)
                .collect(Collectors.toList());
        user.setIdeas(ideas);
        return user;
    }

    public User toChildEntity(){
        User user = new User();
        user.setId(this.getId());
        user.setUsername(this.getUsername());
        return user;
    }
}
