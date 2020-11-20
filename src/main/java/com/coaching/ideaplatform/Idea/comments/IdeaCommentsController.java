package com.coaching.ideaplatform.Idea.comments;

import com.coaching.ideaplatform.Idea.Idea;
import com.coaching.ideaplatform.Idea.IdeaDTO;
import com.coaching.ideaplatform.Idea.IdeaService;
import com.coaching.ideaplatform.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/ideas/{id}/comments")
public class IdeaCommentsController {

    private final IdeaCommentService service;
    private final IdeaService ideaService;
    private final UserService userService;

    public IdeaCommentsController(IdeaCommentService service, IdeaService ideaService, UserService userService) {
        this.service = service;
        this.ideaService = ideaService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long id) {
        List<CommentDTO> comments = service.getComments(id).stream()
                .map(Comment::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long commentId) {
               return new ResponseEntity<>( service.getComment(commentId).toDto(), HttpStatus.OK);
    }

    @PostMapping("/")
    //  @todo je converteert de relaties naar childDto's maar je wil niet dat ze de ideas zien van bv user want is nu leeg doordat niet geconverteerd
    public ResponseEntity<IdeaDTO> addComment(@Valid @RequestBody CommentDTO comment, @PathVariable Long id) {
        comment.setIdea(ideaService.getIdea(id).toDto());
        Long userId = comment.getUser().getId();
        comment.setUser(userService.getUser(userId).toDto());
        Comment comment1 = comment.toEntity();
        return new ResponseEntity<>(service.addComment(comment1, id).toDto(), HttpStatus.OK);
    }


    @PutMapping("{commentId}")
    public ResponseEntity<IdeaDTO> updateComment(@Valid @RequestBody CommentDTO comment, @PathVariable Long id, @PathVariable Long commentId) {
        IdeaDTO ideaDTO = service.updateComment(comment.toEntity(), commentId, id).toDto();
        return new ResponseEntity<>(ideaDTO, HttpStatus.OK);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<String> removeComment(@PathVariable Long commentId, @PathVariable Long id) {
       service.removeComment(commentId, id);
        return new ResponseEntity<>("comment with id " + commentId + " is deleted", HttpStatus.OK);
    }
}
