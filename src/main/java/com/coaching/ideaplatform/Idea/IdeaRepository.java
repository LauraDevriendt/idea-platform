package com.coaching.ideaplatform.Idea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    @Query(value = "SELECT * FROM ideas_users iu JOIN ideas ON iu.ideas_id = ideas.id WHERE ideas.description = :description AND ideas.title = :title AND iu.users_id IN (:userIds)", nativeQuery = true)
    List<Idea> findByTitleAndDescriptionForSameUser(@Param("description") String description, @Param("title") String title,@Param("userIds") List<Long> userIds);

    @Query(value = "SELECT * from ideas_users iu JOIN ideas i ON iu.ideas_id = i.id WHERE public_idea =1", nativeQuery = true)
    List<Idea> findPublicIdeas();
}
