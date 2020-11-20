package com.coaching.ideaplatform.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * from ideas_users iu JOIN users u ON iu.users_id = u.id \n" +
            "JOIN ideas i ON iu.ideas_id = i.id WHERE public_idea = 1", nativeQuery = true)
    List<User> findByUsersWithPublicIdeas();
}
