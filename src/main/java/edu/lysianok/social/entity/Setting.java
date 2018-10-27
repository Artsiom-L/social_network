package edu.lysianok.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "settings")
public class Setting implements Serializable {
    @Id
    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Basic
    @Column(name = "birthday_visibility", nullable = false)
    private boolean birthdayVisibility = true;

    @Basic
    @Column(name = "age_visibility", nullable = false)
    private boolean ageVisibility = true;

    @Basic
    @Column(name = "profile_visibility", nullable = false)
    private boolean profileVisibility = false;

    @Basic
    @Column(name = "location_visibility", nullable = false)
    private boolean locationVisibility = true;

    @Basic
    @Column(name = "gender_visibility", nullable = false)
    private boolean genderVisibility = true;

    @Basic
    @Column(name = "gifts_visibility", nullable = false)
    private boolean giftsVisibility = true;

    @Basic
    @Column(name = "friends_visibility", nullable = false)
    private boolean friendsVisibility = true;

    @Basic
    @Column(name = "group_visibility", nullable = false)
    private boolean groupVisibility = true;

    @Basic
    @Column(name = "non_friends_block", nullable = false)
    private boolean nonFriendsBlock = false;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isBirthdayVisibility() {
        return birthdayVisibility;
    }

    public void setBirthdayVisibility(boolean birthdayVisibility) {
        this.birthdayVisibility = birthdayVisibility;
    }

    public boolean isAgeVisibility() {
        return ageVisibility;
    }

    public void setAgeVisibility(boolean ageVisibility) {
        this.ageVisibility = ageVisibility;
    }

    public boolean isProfileVisibility() {
        return profileVisibility;
    }

    public void setProfileVisibility(boolean profileVisibility) {
        this.profileVisibility = profileVisibility;
    }

    public boolean isLocationVisibility() {
        return locationVisibility;
    }

    public void setLocationVisibility(boolean locationVisibility) {
        this.locationVisibility = locationVisibility;
    }

    public boolean isGenderVisibility() {
        return genderVisibility;
    }

    public void setGenderVisibility(boolean genderVisibility) {
        this.genderVisibility = genderVisibility;
    }

    public boolean isGiftsVisibility() {
        return giftsVisibility;
    }

    public void setGiftsVisibility(boolean giftsVisibility) {
        this.giftsVisibility = giftsVisibility;
    }

    public boolean isFriendsVisibility() {
        return friendsVisibility;
    }

    public void setFriendsVisibility(boolean friendsVisibility) {
        this.friendsVisibility = friendsVisibility;
    }

    public boolean isNonFriendsBlock() {
        return nonFriendsBlock;
    }

    public void setNonFriendsBlock(boolean nonFriendsBlock) {
        this.nonFriendsBlock = nonFriendsBlock;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Setting)) return false;

        Setting setting = (Setting) o;

        if (birthdayVisibility != setting.birthdayVisibility) return false;
        if (ageVisibility != setting.ageVisibility) return false;
        if (profileVisibility != setting.profileVisibility) return false;
        if (locationVisibility != setting.locationVisibility) return false;
        if (genderVisibility != setting.genderVisibility) return false;
        if (giftsVisibility != setting.giftsVisibility) return false;
        if (friendsVisibility != setting.friendsVisibility) return false;
        if (nonFriendsBlock != setting.nonFriendsBlock) return false;
        return userId.equals(setting.userId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + (birthdayVisibility ? 1 : 0);
        result = 31 * result + (ageVisibility ? 1 : 0);
        result = 31 * result + (profileVisibility ? 1 : 0);
        result = 31 * result + (locationVisibility ? 1 : 0);
        result = 31 * result + (genderVisibility ? 1 : 0);
        result = 31 * result + (giftsVisibility ? 1 : 0);
        result = 31 * result + (friendsVisibility ? 1 : 0);
        result = 31 * result + (nonFriendsBlock ? 1 : 0);
        return result;
    }

    public boolean isGroupVisibility() {
        return groupVisibility;
    }

    public void setGroupVisibility(boolean groupVisibility) {
        this.groupVisibility = groupVisibility;
    }
}
