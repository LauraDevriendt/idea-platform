package com.coaching.ideaplatform.users;

import com.coaching.ideaplatform.Idea.Idea;
import com.coaching.ideaplatform.Idea.IdeaService;
import com.coaching.ideaplatform.errors.NotFoundException;
import com.coaching.ideaplatform.errors.NotValidException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final IdeaService ideaService;

    public UserService(UserRepository repository, IdeaService ideaService) {
        this.repository = repository;
        this.ideaService = ideaService;
    }

    public User getUser(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("user not found");
        }
        return user.get();
    }

    public User updateUser(User user) {
        getUser(user.getId());
        verifyUser(user);
        List<Idea> ideas = user.getIdeas();
        ideas.forEach(idea -> idea.addUser(user));
        attachIdeas(ideas);
        return repository.save(user);
    }

    @Transactional
    public User addUser(User user) {
        verifyUser(user);
        List<Idea> ideas = user.getIdeas();
        user.setIdeas(new ArrayList<>());
        User managedUser = repository.save(user);
        ideas.forEach(idea -> idea.addUser(managedUser));
        managedUser.setIdeas(attachIdeas(ideas));
        return repository.save(managedUser);
    }

    private void verifyUser(User user) {
        Optional<User> userByName = repository.findByUsername(user.getUsername());
        if (userByName.isPresent()) {
            if (user.getId() != userByName.get().getId()) {
                throw new NotValidException("user already exists with that name");
            }
        }

    }

    //@todo delete werkt niet
    public void deleteUser(Long id) {
        User user = getUser(id);
        user.getIdeas().forEach(idea -> idea.removeUser(user));
        ideaService.deleteIdeasWithNoUser();
        repository.deleteById(id);
    }

    public List<User> showOnlyUsersWithPublicIdeas() {
        return repository.findByUsersWithPublicIdeas();
    }

    // @todo hoe refactoren? opsplitsen in kleinere functies

    public List<Idea> attachIdeas(List<Idea> ideas) {
        List<Idea> existingIdeas = ideas.stream()
                .filter(idea -> idea.getId() != null)
                .collect(Collectors.toList());
        existingIdeas.forEach(idea -> ideaService.getIdea(idea.getId()));

        List<Idea> newIdeas = ideas.stream().filter(idea -> idea.getId() == null)
                .map(ideaService::addIdea)
                .collect(Collectors.toList());
        existingIdeas.addAll(newIdeas);

       return ideas;
    }
}
