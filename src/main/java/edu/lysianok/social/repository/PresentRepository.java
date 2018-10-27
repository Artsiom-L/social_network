package edu.lysianok.social.repository;

import edu.lysianok.social.entity.Present;
import edu.lysianok.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresentRepository extends JpaRepository<Present, Integer> {
    List<Present> findAllByRecipientOrderByDate(User recipient);

    List<Present> findTop3ByRecipientOrderByDate(User recipient);

    @Query("SELECT DISTINCT present FROM Present as present " +
            "WHERE present.sender = :user OR present.recipient = :user " +
            "ORDER BY present.date DESC")
    List<Present> getStatistic(@Param("user") User user);
}