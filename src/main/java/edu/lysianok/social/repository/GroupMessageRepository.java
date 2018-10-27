package edu.lysianok.social.repository;

import edu.lysianok.social.entity.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMessageRepository extends JpaRepository<GroupMessage, Integer> {
    List<GroupMessage> findAllByGroupIdOrderByDateDesc(Integer groupId);
}
