package edu.lysianok.social.service;

import edu.lysianok.social.dto.MessageDto;
import edu.lysianok.social.entity.Attachment;
import edu.lysianok.social.entity.Message;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.repository.AttachmentRepository;
import edu.lysianok.social.repository.MessageRepository;
import edu.lysianok.social.repository.UserRepository;
import edu.lysianok.social.storage.MessageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private MessageRepository messageRepository;
    private AttachmentRepository attachmentRepository;
    private UserRepository userRepository;
    private UserService userService;
    private MessageStore messageStore;
    private String filesPath;
    private String imagesPath;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                              UserRepository userRepository,
                              AttachmentRepository attachmentRepository,
                              UserService userService,
                              MessageStore messageStore,
                              @Value("${files.path}") String filesPath,
                              @Value("${images.path}") String imagesPath) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.filesPath = filesPath;
        this.imagesPath = imagesPath;
        this.attachmentRepository = attachmentRepository;
        this.userService = userService;
        this.messageStore = messageStore;
        createFolders();
    }

    @Override
    public Message addMessage(MessageDto messageDto, Integer recipientId) {
        Optional<User> recipient = userRepository.findById(recipientId);
        if (!recipient.isPresent()) return null;
        User sender = userService.getCurrentUser();
        Message message = new Message();
        message.setDate(LocalDateTime.now());
        message.setText(HtmlUtils.htmlEscape(messageDto.getText()));
        message.setRecipient(recipient.get());
        message.setSender(sender);
        messageRepository.save(message);
        saveFiles(messageDto, message.getId());
        if (userService.isOnline(recipientId)) {
            messageStore.addMessage(recipient.get().getUsername(), message);
        }
        return message;
    }

    private void saveFiles(MessageDto messageDto, Integer messageId) {
        Attachment attachment;
        int count = messageDto.getFiles().length;
        byte[] file = null;
        try {
            for (MultipartFile multipartFile : messageDto.getFiles()) {
                file = multipartFile.getBytes();
                if (file != null && file.length != 0) {
                    Files.write(Paths.get(filesPath, messageId.toString() + count +
                                    multipartFile.getOriginalFilename()),
                            multipartFile.getBytes());
                    attachment = new Attachment();
                    attachment.setFilename(count + multipartFile.getOriginalFilename());
                    attachment.setMessageId(messageId);
                    attachmentRepository.saveAndFlush(attachment);
                    count--;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.warn(e.getMessage());
        }
    }

    /**
     * checking existence of the folders if they don't exist it will create them
     */
    private void createFolders() {
        Path filePath = Paths.get(filesPath);
        Path imagePath = Paths.get(imagesPath);
        if (!Files.exists(filePath)) filePath.toFile().mkdirs();
        if (!Files.exists(imagePath)) imagePath.toFile().mkdirs();
    }

    @Override
    public void remove(Integer messageId) {
        deleteAttachments(messageId);
        messageRepository.deleteById(messageId);
    }

    private void deleteAttachments(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (!message.isPresent()) return;
        int count = message.get().getAttachments().size();
        for (Attachment attachment : message.get().getAttachments()) {
            try {
                Files.deleteIfExists(Paths.get(filesPath + "/" + messageId + count-- + attachment.getFilename()));
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    @Override
    public Map<User, Message> formDialogues() {
        User currentUser = userService.getCurrentUser();
        List<Message> messages = messageRepository.findAllDialogues(currentUser);
        Map<User, Message> dialogues = new LinkedHashMap<>();
        for (Message message : messages) {
            User sender = message.getSender();
            User recipient = message.getRecipient();
            if (sender.getId().equals(currentUser.getId()) && !dialogues.containsKey(recipient)) {
                dialogues.put(recipient, message);
            } else if (!dialogues.containsKey(sender) && !Objects.equals(currentUser.getId(), sender.getId())) {
                dialogues.put(sender, message);
            }
        }
        return dialogues;
    }
}
