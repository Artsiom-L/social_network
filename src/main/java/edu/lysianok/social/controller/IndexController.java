package edu.lysianok.social.controller;


import edu.lysianok.social.dto.RegistrationDto;
import edu.lysianok.social.dto.SettingsDto;
import edu.lysianok.social.entity.Present;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.entity.WallMessage;
import edu.lysianok.social.repository.PresentRepository;
import edu.lysianok.social.repository.UserRepository;
import edu.lysianok.social.service.UserService;
import edu.lysianok.social.service.WallMessageService;
import edu.lysianok.social.validators.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@Controller
public class IndexController {
    private UserRepository userRepository;
    private PresentRepository presentRepository;
    private UserService userService;
    private final WallMessageService wallMessageService;
    private final RegistrationValidator registrationValidator;

    @Autowired
    public IndexController(UserRepository userRepository,
                           PresentRepository presentRepository,
                           UserService userService,
                           WallMessageService wallMessageService,
                           RegistrationValidator registrationValidator) {
        this.userRepository = userRepository;
        this.presentRepository = presentRepository;
        this.userService = userService;
        this.wallMessageService = wallMessageService;
        this.registrationValidator = registrationValidator;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        User currentUser = userService.getCurrentUser();
        List<Present> presents = presentRepository.findTop3ByRecipientOrderByDate(currentUser);
        List<WallMessage> posts = wallMessageService.getAll();
        model.addAttribute("presents", presents);
        model.addAttribute("user", currentUser);
        model.addAttribute("posts", posts);
        model.addAttribute("groups", currentUser.getFollowedGroups().stream()
                .limit(3).collect(toCollection(ArrayList::new)));
        return "index";
    }

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String showPersonList(Model model) {
        List<User> people = userRepository.findAllByDeletedFalse();
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("pageName", "Person");
        model.addAttribute("people", people);

        return "personList";
    }

    @GetMapping("/filter")
    public String filterByAge(Model model, @RequestParam("youngest") Integer youngest,
                              @RequestParam("eldest") Integer eldest,
                              @RequestParam(value = "friends", required = false) String friends) {
        LocalDate toDate = LocalDate.now().minusYears(youngest);
        LocalDate fromDate = LocalDate.now().minusYears(eldest + 1);
        List<User> people;
        User currentUser = userService.getCurrentUser();
        if (friends != null && friends.equals("")) {
            people = userRepository.filterFriends(fromDate, toDate, currentUser.getFriends());
            model.addAttribute("pageName", "Friend");
        } else {
            people = userRepository.filter(fromDate, toDate);
            model.addAttribute("pageName", "Person");
        }
        model.addAttribute("user", currentUser);
        model.addAttribute("people", people);
        return "personList";
    }

    @GetMapping("/friends")
    public String showFriendList(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("people", currentUser.getFriends());
        model.addAttribute("pageName", "Friend");
        return "personList";
    }

    @PostMapping("/post")
    public ResponseEntity addWallMessage(@RequestBody String text) {
        wallMessageService.addMessage(text);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/post/delete/{postId}")
    public ResponseEntity deleteWallMessage(@PathVariable("postId") Integer postId) {
        wallMessageService.deleteMessage(postId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/search")
    public @ResponseBody
    List<User> search(@RequestBody String query) {
        return userRepository.search(query);
    }

    @GetMapping("/search")
    public String showSearchResult(@RequestParam("q") String query, Model model) {
        List<User> users = userRepository.search(query);
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("people", users);
        model.addAttribute("pageName", "Search");
        return "personList";
    }

    @InitBinder("registrationDto")
    public void addRegistrationValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(registrationValidator);
    }

    @GetMapping("/settings")
    public String settingsForm(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("settings", new SettingsDto());
        model.addAttribute("registrationDto", new RegistrationDto());
        return "settings";
    }

    @PostMapping("/settings/update")
    public String changeCredential(@ModelAttribute("registrationDto") @Valid RegistrationDto registrationDto,
                                   BindingResult bindingResult, Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        if (bindingResult.hasErrors()) {
            model.addAttribute("settings", new SettingsDto());
            return "settings";
        }
        userService.changeCredentials(registrationDto);
        return "redirect:/logout";
    }

    @PostMapping("/settings")
    public String changeSettings(@ModelAttribute("settings") SettingsDto settingsDto) {
        User currentUser = userService.getCurrentUser();
        userService.changeSettings(settingsDto, currentUser.getId());
        return "redirect:/";
    }

    @GetMapping("/support")
    public String getSupport(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "support";
    }
}