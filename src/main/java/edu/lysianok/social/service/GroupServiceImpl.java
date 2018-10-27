package edu.lysianok.social.service;

import edu.lysianok.social.dto.GroupDto;
import edu.lysianok.social.entity.Group;
import edu.lysianok.social.entity.GroupMessage;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.repository.GroupMessageRepository;
import edu.lysianok.social.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    private GroupRepository groupRepository;
    private GroupMessageRepository messageRepository;
    private String groupPath;
    private UserService userService;
    private RestTemplate restTemplate;
    private static final String POSTFIX = "-groupAvatar";
    private static final String DEFAULT_IMAGE_NAME = "default-avatar";
    private static final String DEFAULT_IMAGE =
            "https://cdn.pixabay.com/photo/2016/11/14/17/39/group-1824145_960_720.png";

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            UserService userService,
                            RestTemplate restTemplate,
                            @Value("${group.path}") String groupPath,
                            GroupMessageRepository messageRepository) {
        this.groupRepository = groupRepository;
        this.groupPath = groupPath;
        this.userService = userService;
        this.restTemplate = restTemplate;
        createFolder();
        this.messageRepository = messageRepository;
    }

    @Override
    public void addMessage(String message, Group group) {
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setDate(LocalDateTime.now());
        groupMessage.setGroupId(group.getId());
        groupMessage.setSender(userService.getCurrentUser());
        groupMessage.setText(HtmlUtils.htmlEscape(message));
        messageRepository.save(groupMessage);
    }

    @Override
    public void addGroup(GroupDto groupDto) {
        User currentUser = userService.getCurrentUser();
        Group group = new Group();
        group.setName(groupDto.getName());
        group.setCreator(currentUser);
        groupRepository.save(group);
        group.setGroupPhoto(group.getId() + POSTFIX);
        saveAvatar(groupDto.getGroupPhoto(), group.getId());
    }


    private void saveAvatar(MultipartFile file, Integer groupId) {
        byte[] image = null;
        if (file == null) return;
        try {
            image = file.getBytes();
            if (image.length != 0) {
                Files.write(Paths.get(groupPath, groupId.toString() + POSTFIX), image);
            }
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    @Override
    public void subscribe(Integer groupId) {
        User user = userService.getCurrentUser();
        groupRepository.subscribe(user.getId(), groupId);
    }

    @Override
    public void leaveGroup(Integer groupId) {
        User user = userService.getCurrentUser();
        groupRepository.leaveGroup(user.getId(), groupId);
    }

    @Override
    public void remove(Integer groupId) {
        Optional<Group> targetGroup = groupRepository.findById(groupId);
        if (!targetGroup.isPresent()) return;
        User currentUser = userService.getCurrentUser();
        if (Objects.equals(targetGroup.get().getCreator().getId(), currentUser.getId())) {
            deleteAvatar(groupId);
            groupRepository.delete(targetGroup.get());
        }
    }

    @Override
    public byte[] getGroupImage(String groupId) throws IOException {
        if (isImageExists(groupId)) {
            return Files.readAllBytes(Paths.get(groupPath, groupId + POSTFIX));
        } else if (isDefaultAvatarExists()) {
            return Files.readAllBytes(Paths.get(groupPath, DEFAULT_IMAGE_NAME));
        } else {
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(DEFAULT_IMAGE, HttpMethod.GET,
                    new HttpEntity<Void>(new HttpHeaders()), byte[].class);
            Files.write(Paths.get(groupPath + '/' + DEFAULT_IMAGE_NAME), responseEntity.getBody());
            return Files.readAllBytes(Paths.get(groupPath, DEFAULT_IMAGE_NAME));
        }
    }

    private boolean isImageExists(String groupId) {
        return Files.exists(Paths.get(groupPath, groupId + POSTFIX));
    }

    private void createFolder() {
        Path groupPath = Paths.get(this.groupPath);
        if (!Files.exists(groupPath)) groupPath.toFile().mkdirs();
    }

    private boolean isDefaultAvatarExists() {
        return Files.exists(Paths.get(groupPath, DEFAULT_IMAGE_NAME));
    }

    private void deleteAvatar(Integer id) {
        try {
            Files.deleteIfExists(Paths.get(groupPath + '/' + id.toString() + POSTFIX));
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }
}
