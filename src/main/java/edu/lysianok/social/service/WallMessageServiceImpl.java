package edu.lysianok.social.service;

import edu.lysianok.social.entity.User;
import edu.lysianok.social.entity.WallMessage;
import edu.lysianok.social.repository.WallMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WallMessageServiceImpl implements WallMessageService {
    private UserService userService;
    private WallMessageRepository wallMessageRepository;

    @Autowired
    public WallMessageServiceImpl(UserService userService,
                                  WallMessageRepository wallMessageRepository) {
        this.userService = userService;
        this.wallMessageRepository = wallMessageRepository;
    }

    @Override
    public void addMessage(String text) {
        User currentUser = userService.getCurrentUser();
        WallMessage wallMessage = new WallMessage();
        wallMessage.setAuthor(currentUser);
        wallMessage.setDate(LocalDateTime.now());
        wallMessage.setText(HtmlUtils.htmlEscape(text));
        wallMessageRepository.save(wallMessage);
    }

    @Override
    public void deleteMessage(Integer messageId) {
        Optional<WallMessage> wallMessage = wallMessageRepository.findById(messageId);
        if (!wallMessage.isPresent()) return;
        wallMessageRepository.delete(wallMessage.get());
    }

    @Override
    public List<WallMessage> getAll() {
        User currentUser = userService.getCurrentUser();
        return wallMessageRepository.findAllByAuthorOrderByDateDesc(currentUser);
    }

    @Override
    public List<WallMessage> getAll(User user) {
        return wallMessageRepository.findAllByAuthorOrderByDateDesc(user);
    }
}
