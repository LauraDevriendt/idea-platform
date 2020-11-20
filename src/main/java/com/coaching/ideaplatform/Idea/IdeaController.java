package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.errors.NotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<IdeaDTO>> getIdeas() {
        List<IdeaDTO> ideaDTOS = repository.findPublicIdeas().stream()
                .map(IdeaDTO::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>((ideaDTOS), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdeaDTO> getIdea(@PathVariable Long id) {
        return new ResponseEntity<>(IdeaDTO.toChildDto(service.getIdea(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IdeaDTO> createIdea(@Valid @RequestBody final IdeaDTO ideaDTO) {
        if(ideaDTO.getUsers().stream().anyMatch(userDTO -> userDTO.getId()==null)) {
            throw new NotValidException("you passed users that not exist yet in the database");
        }
        return new ResponseEntity<>(IdeaDTO.toChildDto(service.addIdea(ideaDTO.toEntity())), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdeaDTO> updateIdea(@RequestBody IdeaDTO idea, @PathVariable Long id) {
        return new ResponseEntity<>(IdeaDTO.toChildDto(service.updateIdea(idea.toEntity(), id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIdea(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Idea with id " + id + " is deleted", HttpStatus.OK);
    }
}
