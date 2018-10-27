package edu.lysianok.social.dto;

import org.springframework.web.multipart.MultipartFile;

public class MessageDto {
    private String text;
    private MultipartFile[] files;

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
