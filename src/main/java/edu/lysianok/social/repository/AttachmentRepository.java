package edu.lysianok.social.repository;

import edu.lysianok.social.entity.Attachment;
import edu.lysianok.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    List<Attachment> findAllByMessageId(Integer messageId);

    @Query("SELECT COUNT(attach.id), msg.date FROM Attachment as attach " +
            "LEFT JOIN Message as msg ON msg.id = attach.messageId " +
            "WHERE msg.sender = :currentUser " +
            "GROUP BY msg.date")
    @Transactional(readOnly = true)
    Object[][] getStatistic(@Param("currentUser") User user);
}
