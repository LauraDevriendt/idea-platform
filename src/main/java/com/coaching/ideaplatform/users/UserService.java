package com.coaching.ideaplatform.users;

import com.coaching.ideaplatform.Idea.Idea;
import com.coaching.ideaplatform.Idea.IdeaService;
import com.coaching.ideaplatform.errors.NotFoundException;
import com.coaching.ideaplatform.errors.NotValidException;
import org.springframework.stereotype.Service;

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
        if(user.isEmpty()){
            throw new NotFoundException("user not found");
        }
        return user.get();
    }

    public User updateUser(User user, Long id) {
        attachEntities(user);
        getUser(id);
        user.setId(id);
        return repository.save(user);
    }

    public User verifyAndAddUser(User user) {
        attachEntities(user);
        Optional<User> userByName = repository.findByUsername(user.getUsername());
        if (userByName.isPresent()){
            throw new NotValidException("user already exists with that name");
        }

        return  repository.saveAndFlush(user);
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
    private void attachEntities(User user){
        List<Idea> existingIdeas = user.getIdeas().stream()
                .filter(idea -> idea.getId() != null )
                .map(idea -> idea = ideaService.getIdea(idea.getId()))
                .collect(Collectors.toList());

        List<Idea> newIdeas = user.getIdeas().stream()
                .filter(idea -> idea.getId() == null )
                .map(idea -> idea = ideaService.verifyAndAddIdea(idea))
                .collect(Collectors.toList());

        existingIdeas.addAll(newIdeas);

        user.setIdeas(existingIdeas);

    }
}
