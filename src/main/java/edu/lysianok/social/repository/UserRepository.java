package edu.lysianok.social.repository;

import edu.lysianok.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    List<User> findAllByDeletedFalse();

    @Query(value = "SELECT DISTINCT user FROM User user " +
            "WHERE user.birthDate >= :ageFrom AND user.birthDate <= :ageTo " +
            "AND user.location LIKE %:location% AND user.deleted = 0")
    @Transactional(readOnly = true)
    List<User> findAllByConditions(@Param("ageFrom") LocalDate from,
                                   @Param("ageTo") LocalDate to,
                                   @Param("location") String location);

    @Query(value = "SELECT DISTINCT user FROM User user " +
            "WHERE (user.name LIKE %:request% OR user.surname LIKE %:request%) AND user.deleted = 0")
    @Transactional(readOnly = true)
    List<User> search(@Param("request") String request);

    @Query(value = "SELECT DISTINCT user FROM User user " +
            "WHERE user.birthDate >= :ageFrom AND user.birthDate <= :ageTo AND user.deleted = 0")
    @Transactional(readOnly = true)
    List<User> filter(@Param("ageFrom") LocalDate from, @Param("ageTo") LocalDate to);

    @Query(value = "SELECT DISTINCT user FROM User user " +
            "WHERE user.birthDate >= :ageFrom AND user.birthDate <= :ageTo AND user.deleted = 0 " +
            "AND user IN (:friends)")
    @Transactional(readOnly = true)
    List<User> filterFriends(@Param("ageFrom") LocalDate from,
                             @Param("ageTo") LocalDate to,
                             @Param("friends") Set<User> friends);

    @Modifying
    @Query(value = "INSERT INTO friends VALUES (:userId, :friendId)", nativeQuery = true)
    @Transactional
    void addFriend(@Param("userId") int userId, @Param("friendId") int friendId);

    @Modifying
    @Query(value = "DELETE FROM friends WHERE " +
            "(first_person_id = :userId AND second_person_id = :friendId) OR" +
            " (first_person_id = :friendId AND second_person_id = :userId)", nativeQuery = true)
    @Transactional
    void deleteFriend(@Param("userId") int userId, @Param("friendId") int friendId);
}