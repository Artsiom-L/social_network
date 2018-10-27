package edu.lysianok.social.controller;

import edu.lysianok.social.dto.MessageDto;
import edu.lysianok.social.entity.Attachment;
import edu.lysianok.social.entity.Message;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.repository.AttachmentRepository;
import edu.lysianok.social.repository.MessageRepository;
import edu.lysianok.social.repository.UserRepository;
import edu.lysianok.social.service.MessageService;
import edu.lysianok.social.service.UserService;
import edu.lysianok.social.storage.MessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/message")
public class MessageController {
    private MessageService messageService;
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private MessageStore messageStore;
    private String filesPath;
    private UserService userService;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public MessageController(MessageService messageService,
                             MessageRepository messageRepository,
                             @Value("${files.path}") String filesPath,
                             UserService userService,
                             UserRepository userRepository,
                             MessageStore messageStore,
                             AttachmentRepository attachmentRepository) {
        this.messageService = messageService;
        this.messageRepository = messageRepository;
        this.filesPath = filesPath;
        this.userService = userService;
        this.userRepository = userRepository;
        this.messageStore = messageStore;
        this.attachmentRepository = attachmentRepository;
    }

    @GetMapping("/dialogues")
    public String getDialogues(Model model) {
        User currentUser = userService.getCurrentUser();
        Map<User, Message> dialogues = messageService.formDialogues();
        model.addAttribute("user", currentUser);
        model.addAttribute("dialogues", dialogues.entrySet());
        return "dialogues";
    }

    @GetMapping("/dialog/{userId}")
    public String getDialog(Model model, @PathVariable Integer userId) {
        Optional<User> interlocutor = userRepository.findById(userId);
        if (!interlocutor.isPresent()) return "error/404";
        messageStore.clearMessages(interlocutor.get().getUsername());
        User currentUser = userService.getCurrentUser();
        List<Message> messages = messageRepository.findDialog(currentUser, interlocutor.get());
        model.addAttribute("interlocutor", interlocutor.get());
        model.addAttribute("user", currentUser);
        model.addAttribute("messages", messages);
        return "dialog";
    }

    @PostMapping("/dialog/delete/{userId}")
    public ResponseEntity deleteDialog(@PathVariable Integer userId) {
        Optional<User> interlocutor = userRepository.findById(userId);
        if (!interlocutor.isPresent()) return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        User currentUser = userService.getCurrentUser();
        messageRepository.deleteDialog(interlocutor.get(), currentUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/missed")
    public ResponseEntity<List<Message>> getMissedMessageList() {
        User currentUser = userService.getCurrentUser();
        List<Message> response = new ArrayList<>(messageStore.getMessages(currentUser.getUsername()));
        messageStore.clearMessages(currentUser.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public String deleteMessage(@PathVariable Integer id) throws IOException {
        Optional<Message> currentMessage = messageRepository.findById(id);
        if (!currentMessage.isPresent()) return "404";
        User currentUser = userService.getCurrentUser();
        if (currentUser == currentMessage.get().getRecipient() || currentUser == currentMessage.get().getSender()) {
            messageService.remove(id);
        }
        return "redirect:/";
    }

    @PostMapping("/send/{recipientId}")
    public ResponseEntity sendMessage(@PathVariable Integer recipientId,
                                      @ModelAttribute("message") MessageDto messageDto) throws IOException {
        Message message = messageService.addMessage(messageDto, recipientId);
        message.setAttachments(attachmentRepository.findAllByMessageId(message.getId()));
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/download/{messageId}/{attachmentId}", method = RequestMethod.GET)
    public StreamingResponseBody getSteamingFile(HttpServletResponse response, @PathVariable Integer messageId,
                                                 @PathVariable Integer attachmentId) throws IOException {
        response.setContentType("application/text");
        Optional<Message> message = messageRepository.findById(messageId);
        if (!message.isPresent()) return null;
        Optional<Attachment> currentAttachment = attachmentRepository.findById(attachmentId);
        InputStream inputStream;
        if (currentAttachment.isPresent()) {
            inputStream = new FileInputStream(new File(Paths.get(filesPath, message.get().getId() +
                    currentAttachment.get().getFilename()).toString()));
            response.setHeader("Content-Disposition", "attachment; filename=\"" +
                    currentAttachment.get().getFilename() + "\"");
        } else {
            return null;
        }
        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
        };
    }
}
