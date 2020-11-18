package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.comments.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/ideas")
public class IdeaCommentsController {

    private final IdeaCommentService service;

    public IdeaCommentsController(IdeaCommentService service) {
        this.service = service;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long id) {
        return new ResponseEntity<>(service.getComments(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Idea> addComment(@Valid @RequestBody Comment comment, @PathVariable Long id) {
        return new ResponseEntity<>(service.addComment(comment, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<String> removeComment(@PathVariable Long commentId, @PathVariable Long id) {
       service.removeComment(commentId, id);
        return new ResponseEntity<>("comment with id " + commentId + " is deleted", HttpStatus.OK);
    }
}
