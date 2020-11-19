package com.coaching.ideaplatform.Idea.comments;

import com.coaching.ideaplatform.Idea.Idea;
import com.coaching.ideaplatform.Idea.IdeaService;
import com.coaching.ideaplatform.errors.NotFoundException;
import com.coaching.ideaplatform.errors.NotValidException;
import org.springframework.stereotype.Service;

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

    public Comment getComment(Long id){
        Optional<Comment> comment = repository.findById(id);
        if(comment.isEmpty()){
            throw new NotFoundException("comments isn't found");
        }
        return comment.get();
    }

    public void removeComment(Long commentId, Long id) {
        Comment comment = getComment(commentId);
       if(!ideaService.getIdea(id).getComments().contains(comment)){
           throw new NotValidException("comment doens't belong to this idea");
       }
        ideaService.getIdea(id).removeComment(comment);
        repository.deleteById(commentId);

    }

    public List<Comment> getComments( Long ideaId) {
        Idea idea = ideaService.getIdea(ideaId);
        return idea.getComments();
    }

    public Idea updateComment(Comment comment, Long commentId, Long id) {
        getComment(commentId);
        comment.setId(commentId);
        repository.save(comment);
        return ideaService.getIdea(id);
    }
}
