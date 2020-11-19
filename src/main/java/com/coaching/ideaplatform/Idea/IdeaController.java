package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.errors.NotValidException;
import com.coaching.ideaplatform.users.User;
import com.coaching.ideaplatform.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/ideas")
public class IdeaController {

    private final IdeaRepository repository;
    private final IdeaService service;
    private final UserService userService;

    public IdeaController(IdeaRepository repository, IdeaService service, UserService userService) {
        this.repository = repository;
        this.service = service;
        this.userService = userService;

    }

    @GetMapping("/")
    public ResponseEntity<List<Idea>> getIdeas() {
        return new ResponseEntity<>(service.showPublicIdeas(repository.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idea> getIdea(@PathVariable Long id) {
        return new ResponseEntity<>(service.getIdea(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Idea> createIdea(@Valid @RequestBody final IdeaDTO ideaDTO) {
        if(ideaDTO.getUsers().stream().anyMatch(userDTO -> userDTO.getId()==null)) {
            throw new NotValidException("you passed users that not exist yet in the database");
        }
        return new ResponseEntity<>(service.addIdea(ideaDTO.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idea> updateIdea(@RequestBody Idea idea, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateIdea(idea, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIdea(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Idea with id " + id + " is deleted", HttpStatus.OK);
    }

    @GetMapping("/{id}/users/")
    public ResponseEntity<List<User>> getUsersFromIdea(@PathVariable Long id) {
       return new ResponseEntity<>(service.getIdea(id).getUsers(), HttpStatus.OK);
    }



}
