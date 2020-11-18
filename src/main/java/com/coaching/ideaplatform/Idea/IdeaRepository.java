package com.coaching.ideaplatform.Idea;

import com.coaching.ideaplatform.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    Optional<Idea> findByUserIdAndTitleOrDescription(Long id, String title, String Description);
}
