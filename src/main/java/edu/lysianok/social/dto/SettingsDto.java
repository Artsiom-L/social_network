package edu.lysianok.social.dto;

import org.springframework.web.multipart.MultipartFile;

public class SettingsDto {
    private MultipartFile image;
    private boolean birthdayVisibility;
    private boolean ageVisibility;
    private boolean profileVisibility;
    private boolean locationVisibility;
    private boolean genderVisibility;
    private boolean giftsVisibility;
    private boolean friendsVisibility;
    private boolean groupVisibility;
    private boolean nonFriendsBlock;

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public boolean isGroupVisibility() {
        return groupVisibility;
    }

    public void setGroupVisibility(boolean groupVisibility) {
        this.groupVisibility = groupVisibility;
    }
}
