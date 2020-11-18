package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.comments.Comment;
import com.coaching.ideaplatform.errors.NotFoundException;
import com.coaching.ideaplatform.errors.NotValidException;
import com.coaching.ideaplatform.users.User;
import com.coaching.ideaplatform.users.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IdeaService {

    private final IdeaRepository repository;
  //  private final UserService userService;


    public IdeaService(IdeaRepository repository) {
        this.repository = repository;
       // this.userService = userService;

    }


    public Idea getIdea(Long id) {
        Optional<Idea> idea = repository.findById(id);
        if (idea.isEmpty()) {
            throw new NotFoundException("idea not found");
        }
        return idea.get();
    }

    public Idea updateIdea(Idea idea, Long id) {
        getIdea(id);
        idea.setId(id);
        return repository.save(idea);
    }



    public Idea verifyAndAddIdea(Idea idea) {
        List<Idea> ideas = repository.findByTitleOrDescription(idea.getTitle(), idea.getDescription());
        List<Long> userIdsOfNewIdea = idea.getUsers().stream().map(User::getId).collect(Collectors.toList());
        List<Long> userIdsOfStoredIdeas = ideas.stream().map(Idea::getUsers).flatMap(List::stream).distinct().map(User::getId).collect(Collectors.toList());
        if (userIdsOfNewIdea.stream().anyMatch(userIdsOfStoredIdeas::contains)) {
            throw new NotValidException("this idea already exists for one of the user you have added to the new idea");
        }

        return repository.saveAndFlush(idea);
    }

    public List<Idea> showPublicIdeas(List<Idea> ideas) {
        List<Idea> publicIdeas = new ArrayList<>();
        for (Idea idea : ideas) {
            if (idea.getPublicIdea()) {
                publicIdeas.add(idea);
            }
        }
        return publicIdeas;
    }


    public void deleteIdeasWithNoUser() {
        List<Idea> ideas = repository.findAll();
        List<Idea> collect = ideas.stream().filter(foundIdea -> foundIdea.getUsers().isEmpty()).collect(Collectors.toList());
        collect.forEach(repository::delete);
    }

    /*
    public User getUserFromIdea(Long id, Long userId) {
        List<Long> userIdsFromIdea = getIdea(id).getUsers().stream().map(User::getId).collect(Collectors.toList());
        if (!userIdsFromIdea.contains(userId)) {
            throw new NotValidException("this user doesn't belong to this idea");
        }
        return userService.getUser(id);
    }

    public User updateUserFromIdea(User user, Long id, Long userId) {
        getUserFromIdea(id, userId);
        return userService.updateUser(user, userId);
    }

    public void deleteUserFromIdea(Long userId, Long id) {
        Idea idea = getIdea(id);
        idea.removeUser(getUserFromIdea(id, userId));
        deleteIdeasWithNoUser();
    }

    public User addUserToIdea(Long userId, Long id) {
        Idea idea = getIdea(id);
        if (idea.getUsers().stream().anyMatch(user -> user.getId() == userId)) {
            throw new NotValidException("this user is already in the list of users");
        }
        User userToAdd = userService.getUser(userId);
        idea.addUser(userToAdd);
        return userToAdd;
    }
    */

}
