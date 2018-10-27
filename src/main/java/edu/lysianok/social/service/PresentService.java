package edu.lysianok.social.service;

import edu.lysianok.social.dto.PresentDto;

import java.io.IOException;

public interface PresentService {
    void addPresent(PresentDto presentDto, Integer id) throws IOException;

    void remove(Integer id);

    byte[] getPresentImage(String imageName) throws IOException;
}
