package edu.lysianok.social.controller;

import edu.lysianok.social.dto.GroupDto;
import edu.lysianok.social.dto.MessageDto;
import edu.lysianok.social.dto.PresentDto;
import edu.lysianok.social.dto.RegistrationDto;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.repository.PresentRepository;
import edu.lysianok.social.repository.UserRepository;
import edu.lysianok.social.service.SecurityService;
import edu.lysianok.social.service.UserService;
import edu.lysianok.social.service.WallMessageService;
import edu.lysianok.social.validators.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toCollection;

@Controller
public class UserController {
    private UserService userService;
    private RegistrationValidator registrationValidator;
    private SecurityService securityService;
    private UserRepository userRepository;
    private PresentRepository presentRepository;
    private WallMessageService wallMessageService;

    @Autowired
    public UserController(UserService userService,
                          RegistrationValidator registrationValidator,
                          SecurityService securityService,
                          UserRepository userRepository,
                          PresentRepository presentRepository,
                          WallMessageService wallMessageService) {
        this.userService = userService;
        this.registrationValidator = registrationValidator;
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.presentRepository = presentRepository;
        this.wallMessageService = wallMessageService;
    }

    @InitBinder("registrationDto")
    public void addRegistrationValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(registrationValidator);
    }

    @PostMapping("/delete")
    public void deleteUserAccount(HttpServletRequest request, HttpServletResponse response) {
        User currentUser = userService.getCurrentUser();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        currentUser.setDeleted(true);
        userRepository.saveAndFlush(currentUser);
    }

    @GetMapping("/login")
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Your username or password is invalid.");

        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("registrationDto") @Valid RegistrationDto registrationDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.register(registrationDto);
        securityService.autologin(registrationDto.getUsername(), registrationDto.getPassword());
        return "redirect:/";
    }


    @GetMapping("/user/{id}/image")
    public ResponseEntity getImage(@PathVariable Integer id) throws IOException {
        Optional<User> targetUser = userRepository.findById(id);
        if (!targetUser.isPresent()) {
            return ResponseEntity.badRequest().body("user not found");
        } else if (!userService.isAuthenticated() && !targetUser.get().getSettings().isProfileVisibility()) {
            return ResponseEntity.badRequest().body("access denied");
        }
        return ResponseEntity.ok(userService.getAvatar(id));
    }

    @PostMapping("/add/friend/{id}")
    public ResponseEntity addFriend(@PathVariable Integer id) {
        return userService.addFriend(id) ? new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delete/friend/{id}")
    public ResponseEntity deleteFriend(@PathVariable Integer id) {
        return userService.deleteFriend(id) ? new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/{id}")
    public String getPersonProfile(@PathVariable Integer id, Model model) {
        Optional<User> targetUser = userRepository.findById(id);
        boolean authStatus = userService.isAuthenticated();
        if (!targetUser.isPresent()) {
            return "error/404";
        }
        if (authStatus == false && !targetUser.get().getSettings().isProfileVisibility()) {
            return "error/403";
        }
        model.addAttribute("targetUser", targetUser.get());
        model.addAttribute("isAuthenticated", authStatus);
        if (authStatus == true) {
            User currentUser = userService.getCurrentUser();
            model.addAttribute("user", currentUser);
            model.addAttribute("present", new PresentDto());
            model.addAttribute("isFriend", userService.isFriend(id));
            model.addAttribute("messageDto", new MessageDto());
            model.addAttribute("posts", wallMessageService.getAll(targetUser.get()));
        }
        if (targetUser.get().isDeleted()) {
            return "error/deletedPerson";
        }
        model.addAttribute("friends", targetUser.get().getFriends().stream()
                .limit(3).collect(toCollection(HashSet::new)));
        model.addAttribute("groups", targetUser.get().getFollowedGroups().stream()
                .limit(3).collect(toCollection(ArrayList::new)));
        model.addAttribute("presents", presentRepository.findTop3ByRecipientOrderByDate(targetUser.get()));
        return "personView";
    }

    @GetMapping("/user/friends")
    public ResponseEntity getCurrentUserFriends() {
        User currentUser = userService.getCurrentUser();
        List<Integer> friendsId = new ArrayList<>();
        for (User user : currentUser.getFriends()) {
            friendsId.add(user.getId());
        }
        return ResponseEntity.ok(friendsId);
    }

    @GetMapping("/user/{userId}/friends")
    public String getFriendList(@PathVariable("userId") Integer userId, Model model) {
        User currentUser = userService.getCurrentUser();
        Optional<User> targetUser = userRepository.findById(userId);
        if (!targetUser.isPresent()) return "error/404";
        model.addAttribute("pageName", "Friend");
        model.addAttribute("user", currentUser);
        model.addAttribute("people", targetUser.get().getFriends());
        return "personList";
    }

    @GetMapping("/user/{userId}/groups")
    public String getGroupList(@PathVariable("userId") Integer userId, Model model) {
        User currentUser = userService.getCurrentUser();
        Optional<User> targetUser = userRepository.findById(userId);
        if (!targetUser.isPresent()) return "error/404";
        model.addAttribute("groupDto", new GroupDto());
        model.addAttribute("user", currentUser);
        model.addAttribute("groups", targetUser.get().getFollowedGroups());
        return "groups";
    }
}
