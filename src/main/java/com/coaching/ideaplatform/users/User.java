package com.coaching.ideaplatform.users;

import com.coaching.ideaplatform.Idea.Idea;
import com.coaching.ideaplatform.Idea.IdeaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
public
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",  nullable = false)
    @NotNull
    @NotBlank
    private String username;

    @ManyToMany (mappedBy = "users", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnoreProperties({"comments"})
    private List<Idea> ideas = new ArrayList<>();


}
