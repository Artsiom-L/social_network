package edu.lysianok.social.service;

import edu.lysianok.social.entity.User;
import edu.lysianok.social.entity.WallMessage;

import java.util.List;

public interface WallMessageService {
    void addMessage(String text);

    void deleteMessage(Integer messageId);

    List<WallMessage> getAll();

    List<WallMessage> getAll(User user);
}
