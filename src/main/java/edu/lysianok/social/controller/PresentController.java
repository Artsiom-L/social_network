package edu.lysianok.social.controller;

import edu.lysianok.social.dto.PresentDto;
import edu.lysianok.social.entity.Present;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.repository.PresentRepository;
import edu.lysianok.social.repository.UserRepository;
import edu.lysianok.social.service.PresentService;
import edu.lysianok.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/present")
public class PresentController {
    private PresentRepository presentRepository;
    private UserRepository userRepository;
    private PresentService presentService;
    private UserService userService;

    @Autowired
    public PresentController(PresentRepository presentRepository,
                             UserRepository userRepository,
                             PresentService presentService,
                             UserService userService) {
        this.presentRepository = presentRepository;
        this.userRepository = userRepository;
        this.presentService = presentService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/list/{id}"}, method = RequestMethod.GET)
    public String getPresentList(@PathVariable Integer id, Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) return "404";
        List<Present> allPresents = presentRepository.findAllByRecipientOrderByDate(user.get());
        model.addAttribute("targetUserId", id);
        model.addAttribute("presents", allPresents);

        return "presentList";
    }

    @PostMapping("/send/{id}")
    public String sendMessage(@PathVariable Integer id,
                              @ModelAttribute("present") PresentDto presentDto) throws IOException {
        presentService.addPresent(presentDto, id);
        return "redirect:/";
    }

    @GetMapping("/{presentId}/image")
    public ResponseEntity getImage(@PathVariable Integer presentId) throws IOException {
        Optional<Present> present = presentRepository.findById(presentId);
        if (!present.isPresent()) {
            return ResponseEntity.badRequest().body("present image not found");
        }
        return ResponseEntity.ok(presentService.getPresentImage(present.get().getName()));
    }

    @GetMapping("/image/{name}")
    public ResponseEntity getPresentImage(@PathVariable String name) throws IOException {
        return ResponseEntity.ok(presentService.getPresentImage(name));
    }

    @PostMapping("/delete/{presentId}")
    public ResponseEntity deletePresent(@PathVariable("presentId") Integer presentId) {
        presentService.remove(presentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
