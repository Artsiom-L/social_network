package edu.lysianok.social.repository;

import edu.lysianok.social.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query(value = "INSERT INTO user_groups VALUES (:userId, :groupId)", nativeQuery = true)
    @Modifying
    @Transactional
    void subscribe(@Param("userId") int userId, @Param("groupId") int groupId);

    @Query(value = "DELETE FROM user_groups WHERE user_id = :userId AND group_id = :groupId",
            nativeQuery = true)
    @Modifying
    @Transactional
    void leaveGroup(@Param("userId") int userId, @Param("groupId") int groupId);
}
