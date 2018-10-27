package edu.lysianok.social.service;

import edu.lysianok.social.dto.RegistrationDto;
import edu.lysianok.social.dto.SettingsDto;
import edu.lysianok.social.entity.User;

import java.io.IOException;

public interface UserService {
    void save(User user);

    User getCurrentUser();

    void register(RegistrationDto registrationDto);

    void changeCredentials(RegistrationDto registrationDto);

    void changeSettings(SettingsDto settingsDto, Integer userId);

    boolean addFriend(int id);

    boolean deleteFriend(int id);

    boolean isFriend(int id);

    boolean isAuthenticated();

    byte[] getAvatar(Integer id) throws IOException;

    boolean isOnline(Integer userId);
}
