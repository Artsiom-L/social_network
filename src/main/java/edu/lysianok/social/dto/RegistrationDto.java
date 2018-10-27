package edu.lysianok.social.dto;

import edu.lysianok.social.validators.RegistrationValidator;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.time.LocalDate;

public class RegistrationDto {

    @Size(min = 2, max = 32, message = RegistrationValidator.STRING_SIZE)
    private String username;

    @Size(min = 8, max = 30, message = RegistrationValidator.STRING_SIZE)
    private String password;

    @Size(min = 8, max = 30, message = RegistrationValidator.STRING_SIZE)
    private String passwordConfirm;

    private String birthDate;

    @Range(min = 0, max = 1, message = RegistrationValidator.REQUIRED_VALUE)
    private int gender;

    private String name;

    private String surname;

    private String patronymic;

    private MultipartFile image;

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
