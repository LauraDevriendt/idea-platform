package com.coaching.ideaplatform.Idea.comments;

import com.coaching.ideaplatform.Idea.Idea;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/ideas/{id}/comments")
public class IdeaCommentsController {

    private final IdeaCommentService service;

    public IdeaCommentsController(IdeaCommentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long id) {
        return new ResponseEntity<>(service.getComments(id), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId) {
        return new ResponseEntity<>(service.getComment(commentId), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<Idea> addComment(@Valid @RequestBody Comment comment, @PathVariable Long id) {
        return new ResponseEntity<>(service.addComment(comment, id), HttpStatus.OK);
    }


    @PutMapping("{commentId}")
    public ResponseEntity<Idea> updateComment(@Valid @RequestBody Comment comment, @PathVariable Long id, @PathVariable Long commentId) {
        return new ResponseEntity<>(service.updateComment(comment, commentId, id), HttpStatus.OK);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<String> removeComment(@PathVariable Long commentId, @PathVariable Long id) {
       service.removeComment(commentId, id);
        return new ResponseEntity<>("comment with id " + commentId + " is deleted", HttpStatus.OK);
    }


}
