package edu.lysianok.social.service;

import edu.lysianok.social.dto.RegistrationDto;
import edu.lysianok.social.dto.SettingsDto;
import edu.lysianok.social.entity.Setting;
import edu.lysianok.social.entity.User;
import edu.lysianok.social.repository.SettingRepository;
import edu.lysianok.social.repository.UserRepository;
import edu.lysianok.social.storage.ActiveUserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String DEFAULT_IMAGE =
            "https://cdn.pixabay.com/photo/2016/03/31/19/57/avatar-1295406_960_720.png";
    private String imagesPath;
    private UserRepository userRepository;
    private SettingRepository settingRepository;
    private ActiveUserStore activeUserStore;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RestTemplate restTemplate;
    private final SecurityService securityService;
    private static final String POSTFIX = "-avatar";
    private static final String DEFAULT_IMAGE_NAME = "default-avatar";

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           @Value("${images.path}") String imagesPath,
                           RestTemplate restTemplate,
                           SettingRepository settingRepository,
                           ActiveUserStore activeUserStore,
                           SecurityService securityService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.imagesPath = imagesPath;
        this.restTemplate = restTemplate;
        this.settingRepository = settingRepository;
        this.activeUserStore = activeUserStore;
        this.securityService = securityService;
    }

    @Override
    public User getCurrentUser() {
        UserDetails userDetails = UserDetails.class.cast(SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void register(RegistrationDto registrationDto) {
        User user = new User();
        Setting setting = new Setting();
        fillUser(user, registrationDto);
        user.setDeleted(false);
        userRepository.save(user);
        setting.setUserId(user.getId());
        settingRepository.save(setting);
        saveAvatar(registrationDto.getImage(), user.getId());
    }

    @Override
    public void changeCredentials(RegistrationDto registrationDto) {
        User user = getCurrentUser();
        fillUser(user, registrationDto);
        userRepository.saveAndFlush(user);
    }

    private void fillUser(User user, RegistrationDto registrationDto) {
        user.setUsername(HtmlUtils.htmlEscape(registrationDto.getUsername()));
        user.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        user.setBirthDate(LocalDate.parse(registrationDto.getBirthDate()));
        user.setGender((byte) registrationDto.getGender());
        user.setName(HtmlUtils.htmlEscape(registrationDto.getName()));
        user.setSurname(HtmlUtils.htmlEscape(registrationDto.getSurname()));
        user.setLocation(HtmlUtils.htmlEscape(registrationDto.getLocation()));
        if (!registrationDto.getPatronymic().isEmpty()) {
            user.setPatronymic(registrationDto.getPatronymic());
        }
    }

    private void saveAvatar(MultipartFile file, Integer userId) {
        byte[] image = null;
        if (file == null) return;
        try {
            image = file.getBytes();
            if (image.length != 0) {
                Files.write(Paths.get(imagesPath, userId.toString() + POSTFIX), image);
            }
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    @Override
    public byte[] getAvatar(Integer userId) throws IOException {
        if (isImageExists(userId)) {
            return Files.readAllBytes(Paths.get(imagesPath, userId.toString() + POSTFIX));
        } else if (isDefaultImageExists()) {
            return Files.readAllBytes(Paths.get(imagesPath, DEFAULT_IMAGE_NAME));
        } else {
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(DEFAULT_IMAGE, HttpMethod.GET,
                    new HttpEntity<Void>(new HttpHeaders()), byte[].class);
            Files.write(Paths.get(imagesPath + '/' + DEFAULT_IMAGE_NAME), responseEntity.getBody());
            return Files.readAllBytes(Paths.get(imagesPath, DEFAULT_IMAGE_NAME));
        }
    }

    @Override
    public boolean isOnline(Integer userId) {
        User searchedUser = userRepository.findById(userId).get();
        for (String username : activeUserStore.getUsers()) {
            if (searchedUser.getUsername().equals(username)) return true;
        }
        return false;
    }

    @Override
    public void changeSettings(SettingsDto settingsDto, Integer userId) {
        try {
            if (settingsDto.getImage() != null && settingsDto.getImage().getBytes().length > 0) {
                if (isImageExists(userId)) {
                    deleteAvatar(userId);
                }
                saveAvatar(settingsDto.getImage(), userId);
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        Setting setting = settingRepository.findSettingByUser(getCurrentUser());
        setting.setAgeVisibility(settingsDto.isAgeVisibility());
        setting.setBirthdayVisibility(settingsDto.isBirthdayVisibility());
        setting.setFriendsVisibility(settingsDto.isFriendsVisibility());
        setting.setGenderVisibility(settingsDto.isGenderVisibility());
        setting.setGiftsVisibility(settingsDto.isGiftsVisibility());
        setting.setProfileVisibility(settingsDto.isProfileVisibility());
        setting.setLocationVisibility(settingsDto.isLocationVisibility());
        setting.setNonFriendsBlock(settingsDto.isNonFriendsBlock());
        setting.setGroupVisibility(settingsDto.isGroupVisibility());
        settingRepository.save(setting);
    }


    @Override
    public boolean addFriend(int id) {
        User currentUser = getCurrentUser();
        Optional<User> targetUser = userRepository.findById(id);
        if (!targetUser.isPresent()) return false;
        for (User user : currentUser.getFriends()) {
            if (id == user.getId()) return false;
        }
        userRepository.addFriend(currentUser.getId(), id);
        return true;
    }

    @Override
    public boolean deleteFriend(int id) {
        User currentUser = getCurrentUser();
        for (User user : currentUser.getFriends()) {
            if (id == user.getId()) {
                userRepository.deleteFriend(currentUser.getId(), id);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFriend(int id) {
        User currentUser = getCurrentUser();
        for (User user : currentUser.getFriends()) {
            if (id == user.getId()) return true;
        }
        return false;
    }

    @Override
    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !auth.getPrincipal().equals("anonymousUser");
    }

    private void deleteAvatar(Integer id) {
        try {
            Files.deleteIfExists(Paths.get(imagesPath + '/' + id.toString() + POSTFIX));
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    private boolean isImageExists(Integer userId) {
        return Files.exists(Paths.get(imagesPath, userId.toString() + POSTFIX));
    }

    private boolean isDefaultImageExists() {
        return Files.exists(Paths.get(imagesPath, DEFAULT_IMAGE_NAME));
    }
}
