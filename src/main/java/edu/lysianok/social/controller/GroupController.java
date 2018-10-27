package edu.lysianok.social.controller;

import edu.lysianok.social.dto.GroupDto;
import edu.lysianok.social.entity.Group;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.repository.GroupMessageRepository;
import edu.lysianok.social.repository.GroupRepository;
import edu.lysianok.social.service.GroupService;
import edu.lysianok.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/group")
public class GroupController {
    private GroupService groupService;
    private GroupRepository groupRepository;
    private UserService userService;
    private GroupMessageRepository messageRepository;

    @Autowired
    public GroupController(GroupService groupService,
                           GroupRepository groupRepository,
                           UserService userService,
                           GroupMessageRepository messageRepository) {
        this.groupService = groupService;
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/list")
    public String getAllGroups(Model model) {
        List<Group> groups = groupRepository.findAll();
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("groups", groups);
        model.addAttribute("groupDto", new GroupDto());
        return "groups";
    }

    @GetMapping("/{groupId}")
    public String getGroup(@PathVariable("groupId") Integer groupId, Model model) {
        User currentUser = userService.getCurrentUser();
        Optional<Group> group = groupRepository.findById(groupId);
        if (!group.isPresent()) return "error/404";
        model.addAttribute("user", currentUser);
        model.addAttribute("group", group.get());
        model.addAttribute("messages", messageRepository.findAllByGroupIdOrderByDateDesc(groupId));
        return "group";
    }

    @PostMapping("/post/{groupId}")
    public ResponseEntity addGroupMessage(@RequestBody String text, @PathVariable("groupId") Integer groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (!group.isPresent()) return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        groupService.addMessage(text, group.get());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity create(@ModelAttribute("groupDto") GroupDto groupDto) {
        groupService.addGroup(groupDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/subscribe/{groupId}")
    public ResponseEntity subscribe(@PathVariable Integer groupId) {
        groupService.subscribe(groupId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/leave/{groupId}")
    public ResponseEntity leaveGroup(@PathVariable Integer groupId) {
        groupService.leaveGroup(groupId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{groupId}/image")
    public ResponseEntity getImage(@PathVariable Integer groupId) throws IOException {
        Optional<Group> targetUser = groupRepository.findById(groupId);
        if (!targetUser.isPresent()) {
            return ResponseEntity.badRequest().body("group not found");
        }
        return ResponseEntity.ok(groupService.getGroupImage(groupId.toString()));
    }

    @GetMapping("/subscribed")
    public ResponseEntity getSubscribedGroups() {
        List<Group> subscribedGroups = userService.getCurrentUser().getFollowedGroups();
        List<Integer> groupIds = new ArrayList<>();
        for (Group group : subscribedGroups) {
            groupIds.add(group.getId());
        }
        return ResponseEntity.ok(groupIds);
    }
}
