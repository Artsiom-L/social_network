package edu.lysianok.social.controller;

import edu.lysianok.social.entity.Group;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RestApiController {
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private final WallMessageRepository wallMessageRepository;
    private final AttachmentRepository attachmentRepository;
    private final PresentRepository presentRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public RestApiController(UserRepository userRepository,
                             MessageRepository messageRepository,
                             WallMessageRepository wallMessageRepository,
                             AttachmentRepository attachmentRepository,
                             PresentRepository presentRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.wallMessageRepository = wallMessageRepository;
        this.attachmentRepository = attachmentRepository;
        this.presentRepository = presentRepository;
        this.groupRepository = groupRepository;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") Integer userId,
                                  @RequestParam(value = "add", required = false) String add) {
        Optional<User> user = userRepository.findById(userId);
        Map<String, Object> result = new LinkedHashMap<>();
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        result.put("user", user.get());
        if (add != null) {
            if (add.contains("friends")) result.put("friends", user.get().getFriends());
            if (add.contains("groups")) result.put("groups", user.get().getFollowedGroups());
            if (add.contains("dialogues")) result.put("dialogues", messageRepository.getCountDialogues(user.get()));
            if (add.contains("settings")) result.put("settings", user.get().getSettings());
            if (add.contains("posts")) result.put("posts", wallMessageRepository.countWallMessagesByAuthor(user.get()));
            if (add.contains("files")) result.put("files", attachmentRepository.getStatistic(user.get()));
            if (add.contains("presents")) result.put("presents", presentRepository.getStatistic(user.get()));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity getGroup(@PathVariable("groupId") Integer groupId,
                                   @RequestParam(value = "add", required = false) String add) {
        Map<String, Object> result = new LinkedHashMap<>();
        Optional<Group> group = groupRepository.findById(groupId);
        if (!group.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        result.put("group", group.get());
        if (add != null) {
            if (add.contains("subscribers")) result.put("subscribers", group.get().getSubscribers());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity getSpecificUsers(@RequestParam(value = "age", required = false) String age,
                                           @RequestParam(value = "location", required = false) String location) {
        LocalDate requiredAgeFrom = LocalDate.now().minusYears(150), requiredAgeTo = LocalDate.now().plusYears(1);
        String requiredLocation = "";
        if (age == null && location == null)
            return new ResponseEntity<>(userRepository.findAllByDeletedFalse(), HttpStatus.OK);
        if (age != null) {
            Long longAge = Long.parseLong(age);
            requiredAgeFrom = LocalDate.now().minusYears(longAge + 1);
            requiredAgeTo = LocalDate.now().minusYears(longAge);
        }
        if (location != null) {
            requiredLocation = location;
        }
        List<User> result = userRepository.findAllByConditions(requiredAgeFrom, requiredAgeTo, requiredLocation);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
