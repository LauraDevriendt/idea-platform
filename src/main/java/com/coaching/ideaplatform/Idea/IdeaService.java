package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.comments.Comment;
import com.coaching.ideaplatform.errors.NotFoundException;
import com.coaching.ideaplatform.errors.NotValidException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IdeaService {

    private final IdeaRepository repository;


    public IdeaService(IdeaRepository repository) {
        this.repository = repository;
    }

    public Idea getIdea(Long id) {
        Optional<Idea> idea = repository.findById(id);
        if(idea.isEmpty()){
            throw new NotFoundException("idea not found");
        }
        return idea.get();
    }

    public Idea updateIdea(Idea idea, Long id) {
        getIdea(id);
        idea.setId(id);
        return repository.save(idea);
    }

    // @todo stackoverflow error voor verify
    public Idea verifyAndAddIdea(Idea idea) {
        Optional<Idea> ideaByName = repository.findByUserIdAndTitleOrDescription(idea.getUser().getId(), idea.getTitle(), idea.getDescription());
        if (ideaByName.isPresent()){
            throw new NotValidException("idea already exists with that title/description");
        }

        return  repository.saveAndFlush(idea);
    }

    public List<Idea> showPublicIdeas(List<Idea> ideas) {
        List<Idea> publicIdeas = new ArrayList<>();
        for (Idea idea: ideas) {
            if (idea.getPublicIdea()) {
                publicIdeas.add(idea);
            }
        }
        return publicIdeas;
    }



}
