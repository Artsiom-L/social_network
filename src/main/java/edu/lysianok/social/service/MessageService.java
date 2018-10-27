package edu.lysianok.social.service;

import edu.lysianok.social.dto.MessageDto;
import edu.lysianok.social.entity.Message;
import edu.lysianok.social.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MessageService {
    Message addMessage(MessageDto messageDto, Integer id) throws IOException;

    void remove(Integer id);

    Map<User, Message> formDialogues();
}
