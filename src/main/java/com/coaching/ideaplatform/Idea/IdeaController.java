package com.coaching.ideaplatform.Idea;

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

    public IdeaController(IdeaRepository repository, IdeaService service) {
        this.repository = repository;
        this.service = service;
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
    public ResponseEntity<Idea> createIdea(@Valid @RequestBody final Idea idea) {
        return new ResponseEntity<>(service.verifyAndAddIdea(idea), HttpStatus.OK);
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

}
