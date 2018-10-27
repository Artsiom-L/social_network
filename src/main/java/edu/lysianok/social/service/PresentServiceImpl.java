package edu.lysianok.social.service;

import edu.lysianok.social.dto.PresentDto;
import edu.lysianok.social.entity.Present;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.repository.PresentRepository;
import edu.lysianok.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class PresentServiceImpl implements PresentService {
    private UserRepository userRepository;
    private PresentRepository presentRepository;
    private UserService userService;
    private String presentPath;
    private RestTemplate restTemplate;
    private static final String POSTFIX = "-present";
    private static final Map<String, String> presents = new HashMap<>();

    static {
        presents.put("first", "https://cdn.pixabay.com/photo/2014/04/02/17/03/present-307776_960_720.png");
        presents.put("second", "https://cdn.pixabay.com/photo/2017/11/02/21/54/present-2912709_960_720.png");
        presents.put("third", "https://cdn.pixabay.com/photo/2016/05/26/17/13/present-1417619_1280.png");
    }

    @Autowired
    public PresentServiceImpl(UserRepository userRepository,
                              PresentRepository presentRepository,
                              UserService userService,
                              RestTemplate restTemplate,
                              @Value("${present.path}") String presentPath) {
        this.userRepository = userRepository;
        this.presentRepository = presentRepository;
        this.userService = userService;
        this.presentPath = presentPath;
        this.restTemplate = restTemplate;
        createFolder();
    }

    @Override
    public void addPresent(PresentDto presentDto, Integer recipientId) throws IOException {
        Optional<User> recipient = userRepository.findById(recipientId);
        if (!recipient.isPresent()) return;
        User sender = userService.getCurrentUser();
        Present present = new Present();
        present.setDate(LocalDateTime.now());
        present.setName(presentDto.getName());
        present.setSignature(HtmlUtils.htmlEscape(presentDto.getSignature()));
        present.setRecipient(recipient.get());
        present.setSender(sender);
        presentRepository.save(present);
    }

    @Override
    public void remove(Integer presentId) {
        User currentUser = userService.getCurrentUser();
        Optional<Present> present = presentRepository.findById(presentId);
        if (!present.isPresent()) return;
        if (Objects.equals(currentUser.getId(), present.get().getRecipient().getId())) {
            presentRepository.delete(present.get());
        }
    }

    @Override
    public byte[] getPresentImage(String imageName) throws IOException {
        if (!presents.containsKey(imageName)) {
            return new byte[0];
        } else {
            if (isImageExists(imageName)) {
                return Files.readAllBytes(Paths.get(presentPath, imageName + POSTFIX));
            } else {
                String presentLink = presents.get(imageName);
                ResponseEntity<byte[]> responseEntity = restTemplate.exchange(presentLink, HttpMethod.GET,
                        new HttpEntity<Void>(new HttpHeaders()), byte[].class);
                Files.write(Paths.get(presentPath + '/' + imageName + POSTFIX), responseEntity.getBody());
                return Files.readAllBytes(Paths.get(presentPath, imageName + POSTFIX));
            }
        }
    }

    private boolean isImageExists(String presentName) {
        return Files.exists(Paths.get(presentPath, presentName + POSTFIX));
    }

    private void createFolder() {
        Path presentPath = Paths.get(this.presentPath);
        if (!Files.exists(presentPath)) presentPath.toFile().mkdirs();
    }
}
