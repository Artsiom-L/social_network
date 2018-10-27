package edu.lysianok.social.service;

import edu.lysianok.social.dto.GroupDto;
import edu.lysianok.social.entity.Group;
import edu.lysianok.social.entity.GroupMessage;

import java.io.IOException;

public interface GroupService {
    void addMessage(String message, Group group);

    void addGroup(GroupDto groupDto);

    void subscribe(Integer groupId);

    void leaveGroup(Integer groupId);

    void remove(Integer id);

    byte[] getGroupImage(String imageName) throws IOException;
}
