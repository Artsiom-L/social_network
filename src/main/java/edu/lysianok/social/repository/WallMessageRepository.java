package edu.lysianok.social.repository;

import edu.lysianok.social.entity.User;
import edu.lysianok.social.entity.WallMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WallMessageRepository extends JpaRepository<WallMessage, Integer> {
    List<WallMessage> findAllByAuthorOrderByDateDesc(User author);

    Integer countWallMessagesByAuthor(User author);
}
