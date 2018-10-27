package edu.lysianok.social.repository;

import edu.lysianok.social.entity.Setting;
import edu.lysianok.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Integer> {
    Setting findSettingByUser(User user);
}
