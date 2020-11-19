package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.errors.NotFoundException;
import com.coaching.ideaplatform.errors.NotValidException;
import com.coaching.ideaplatform.users.User;
import com.coaching.ideaplatform.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IdeaService {

    private final IdeaRepository repository;
    private final UserRepository userRepository;
  //  private final UserService userService;


    public IdeaService(IdeaRepository repository, UserRepository userRepository) {
        this.repository = repository;
       // this.userService = userService;
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
        return repository.save(idea);
    }



    public Idea verifyAndAddIdea(Idea idea) {
        verifyIdea(idea);

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
        collect.forEach(idea -> repository.deleteById(idea.getId()));
    }

    public void verifyIdea(Idea idea) {
        List<Idea> ideas = repository.findByTitleOrDescription(idea.getTitle(), idea.getDescription());
        if (idea.getUsers() == null) {
            throw new NotValidException("this idea belongs to anyone");
        } else {
            List<User> passedUsers = idea.getUsers();
            List<User> retrievedUsers = new ArrayList<>();

            for (User user : passedUsers) {
                if (userRepository.findById(user.getId()).isEmpty()) {
                    throw new NotValidException("the user of the idea with id " + user.getId() + " doesn't exist yet, create user first");
                } else {
                    retrievedUsers.add(userRepository.findById(user.getId()).get());
                }
            }
            idea.setUsers(retrievedUsers.stream().distinct().collect(Collectors.toList()));
        }
        List<Long> userIdsOfNewIdea = idea.getUsers().stream().map(User::getId).collect(Collectors.toList());
        List<Long> userIdsOfStoredIdeas = ideas.stream().map(Idea::getUsers).flatMap(List::stream).distinct().map(User::getId).collect(Collectors.toList());
        if (userIdsOfStoredIdeas.stream().anyMatch(userIdsOfNewIdea::contains)) {
            throw new NotValidException("this idea already exists for one of the user you have added to the new idea");
        }

    }

}
