package edu.lysianok.social.repository;

import edu.lysianok.social.entity.Message;
import edu.lysianok.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT DISTINCT message FROM Message message " +
            "WHERE (message.recipient = :firstUser AND message.sender = :secondUser) OR " +
            "(message.recipient = :secondUser AND message.sender = :firstUser)")
    @Transactional(readOnly = true)
    List<Message> findDialog(@Param("firstUser") User firstUser, @Param("secondUser") User secondUser);

    @Query("SELECT DISTINCT message FROM Message message " +
            "WHERE message.recipient = :user OR message.sender = :user " +
            "ORDER BY message.date DESC")
    @Transactional(readOnly = true)
    List<Message> findAllDialogues(@Param("user") User user);

    @Query("SELECT COUNT(DISTINCT usr.id) FROM Message as msg " +
            "LEFT JOIN User as usr ON (msg.recipient = usr.id OR msg.sender = usr.id) " +
            "AND (msg.recipient = :user OR msg.sender = :user) " +
            "WHERE usr is not :user")
    @Transactional(readOnly = true)
    Integer getCountDialogues(@Param("user") User user);

    @Query(value = "DELETE FROM messages WHERE (source = :interlocutor AND recipient = :currentUser) OR " +
            "(source = :currentUser AND recipient = :interlocutor)", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteDialog(@Param("interlocutor") User user, @Param("currentUser") User currentUser);
}