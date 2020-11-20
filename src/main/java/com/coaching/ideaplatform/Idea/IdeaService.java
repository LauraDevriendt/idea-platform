package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.errors.NotFoundException;
import com.coaching.ideaplatform.errors.NotValidException;
import com.coaching.ideaplatform.users.User;
import com.coaching.ideaplatform.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IdeaService {

    private final IdeaRepository repository;
    private final UserRepository userRepository;

    public IdeaService(IdeaRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;

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
        verifyIdea(idea);
        return repository.save(idea);
    }

    public Idea addIdea(Idea idea) {
        List<User> users = checkUserExistence(idea.getUsers());
        idea.setUsers(users);
        verifyIdea(idea);

        return repository.saveAndFlush(idea);
    }

    private List<User> checkUserExistence(List<User> usersFromIdea) {
        return usersFromIdea.stream()
                .map(user -> {
                    Optional<User> foundUser = userRepository.findById(user.getId());
                    if (foundUser.isEmpty()) {
                        throw new NotFoundException("user: " + user.getId() +":"+ user.getUsername() + " not found");
                    }
                    return foundUser.get();
                })
                .collect(Collectors.toList());
    }

    public void deleteIdeasWithNoUser() {
        List<Idea> ideas = repository.findAll();
        List<Idea> collect = ideas.stream().filter(foundIdea -> foundIdea.getUsers().isEmpty()).collect(Collectors.toList());
        collect.forEach(idea -> repository.deleteById(idea.getId()));
    }

    public void verifyIdea(Idea idea) {
        List<Long> userIds = idea.getUsers().stream().map(User::getId).collect(Collectors.toList());
        List<Idea> foundIdeas = repository.findByTitleAndDescriptionForSameUser(idea.getDescription(), idea.getTitle(), userIds);
        if (foundIdeas.size()>0){
            throw new NotValidException("this idea already exists for one of the users");
        }
    }
}
