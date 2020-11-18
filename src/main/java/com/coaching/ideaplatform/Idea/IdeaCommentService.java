package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.comments.Comment;
import com.coaching.ideaplatform.comments.CommentRepository;
import com.coaching.ideaplatform.errors.NotFoundException;
import com.coaching.ideaplatform.errors.NotValidException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IdeaCommentService {

    private final CommentRepository repository;
    private final IdeaService ideaService;


    public IdeaCommentService(CommentRepository repository, IdeaService ideaService) {
        this.repository = repository;
        this.ideaService = ideaService;
    }


    public Idea addComment(Comment comment, Long id) {
        comment.setIdea(ideaService.getIdea(id));
        repository.save(comment);
        return ideaService.getIdea(id);
    }

    private Comment getComment(Long id){
        Optional<Comment> comment = repository.findById(id);
        if(comment.isEmpty()){
            throw new NotFoundException("comments isn't found");
        }
        return comment.get();
    }

    public Idea removeComment(Long commentId, Long id) {
        Comment comment = getComment(commentId);
       if(!ideaService.getIdea(id).getComments().contains(comment)){
           throw new NotValidException("comment doens't belong to this idea");
       }
        repository.deleteById(commentId);
        return ideaService.getIdea(id);
    }

    public List<Comment> getComments( Long ideaId) {
        Idea idea = ideaService.getIdea(ideaId);
        return idea.getComments();
    }

}
