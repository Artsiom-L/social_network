package edu.lysianok.social.dto;

import org.springframework.web.multipart.MultipartFile;

public class GroupDto {
    private String name;

    private MultipartFile groupPhoto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(MultipartFile groupPhoto) {
        this.groupPhoto = groupPhoto;
    }
}
