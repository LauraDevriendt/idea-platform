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

    public User updateUser(User user, Long id) {
        getUser(id);
        user.setId(id);
        verifyUser(user);
        attachIdeas(user);
        return repository.save(user);
    }

    @Transactional
    public User verifyAndAddUser(User user) {
      verifyUser(user);
        User userReadyForSave = attachIdeas(user);
        return repository.saveAndFlush(userReadyForSave);
    }

    private void verifyUser(User user){
        Optional<User> userByName = repository.findByUsername(user.getUsername());
        if (userByName.isPresent()){
            if(user.getId()!= userByName.get().getId()) {
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
        List<User> users = repository.findAll();
        List<User> usersWithPublicIdeas = new ArrayList<>();
        for (User user : users) {
            List<Idea> publicIdeas = ideaService.showPublicIdeas(user.getIdeas());
            user.setIdeas(publicIdeas);
            usersWithPublicIdeas.add(user);
        }
        return usersWithPublicIdeas;
    }

    // @todo hoe transactioneel maken?
    public User attachIdeas(User user) {
        List<Idea> ideas = user.getIdeas();
        user.setIdeas(new ArrayList<>());
        if(user.getId() ==null || repository.findById(user.getId()).isEmpty()){
            repository.saveAndFlush(user);
        }
        ideas.forEach(idea -> idea.addUser(user));
        List<Idea> existingIdeas = ideas.stream().filter(idea -> idea.getId() != null).collect(Collectors.toList());
        existingIdeas.forEach(idea -> ideaService.getIdea(idea.getId()));
        List<Idea> newIdeas = ideas.stream().filter(idea -> idea.getId() == null).map(ideaService::verifyAndAddIdea).collect(Collectors.toList());
        existingIdeas.addAll(newIdeas);
        user.setIdeas(existingIdeas);
        return user;
    }
}
