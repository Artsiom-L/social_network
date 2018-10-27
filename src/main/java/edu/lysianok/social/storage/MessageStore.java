package edu.lysianok.social.storage;

import edu.lysianok.social.entity.Message;

import java.util.*;

public class MessageStore {
    private Map<String, List<Message>> messages = new HashMap<>();

    public List<Message> getMessages(String username) {
        if (messages.containsKey(username)) {
            return messages.get(username);
        }
        return Collections.emptyList();
    }

    public void clearMessages(String username) {
        if (messages.containsKey(username)) {
            messages.get(username).clear();
        }
    }

    public void deleteMessages(String username) {
        messages.remove(username);
    }

    public void addMessage(String username, Message message) {
        if (messages.containsKey(username)) {
            messages.get(username).add(message);
        } else {
            messages.put(username, new ArrayList<>());
            messages.get(username).add(message);
        }
    }
}
