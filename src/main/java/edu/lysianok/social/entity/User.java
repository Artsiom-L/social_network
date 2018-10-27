package edu.lysianok.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "birth_date", nullable = true)
    private LocalDate birthDate;

    @Basic
    @Column(name = "gender", nullable = true)
    private Byte gender;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @JsonIgnore
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Basic
    @Column(name = "patronymic", nullable = true, length = 50)
    private String patronymic;

    @Basic
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Basic
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Basic
    @Column(name = "location", nullable = true, length = 255)
    private String location;

    @JsonIgnore
    @Basic
    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<WallMessage> messages;

    @JsonIgnore
    @OneToMany(mappedBy = "creator")
    private List<Group> createdGroups;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Setting settings;

    @JsonIgnore
    @OneToMany(mappedBy = "recipient")
    private List<Present> presents;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "friends",
            joinColumns = {@JoinColumn(name = "second_person_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "first_person_id", referencedColumnName = "user_id")})
    private List<User> acceptedFriends;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "friends",
            joinColumns = {@JoinColumn(name = "first_person_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "second_person_id", referencedColumnName = "user_id")})
    private List<User> invitedFriends;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_groups",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "group_id")})
    private List<Group> followedGroups;

    public List<Group> getFollowedGroups() {
        return followedGroups;
    }

    public void setFollowedGroups(List<Group> followedGroups) {
        this.followedGroups = followedGroups;
    }

    @Transient
    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    public List<Present> getPresents() {
        return presents;
    }

    public void setPresents(List<Present> presents) {
        this.presents = presents;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (deleted != user.deleted) return false;
        if (!id.equals(user.id)) return false;
        if (birthDate != null ? !birthDate.equals(user.birthDate) : user.birthDate != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (patronymic != null ? !patronymic.equals(user.patronymic) : user.patronymic != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        return location != null ? location.equals(user.location) : user.location == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }

    public List<Group> getCreatedGroups() {
        return createdGroups;
    }

    public void setCreatedGroups(List<Group> createdGroups) {
        this.createdGroups = createdGroups;
    }


    public Setting getSettings() {
        return settings;
    }

    public void setSettings(Setting settings) {
        this.settings = settings;
    }


    public List<User> getAcceptedFriends() {
        return acceptedFriends;
    }


    public List<User> getInvitedFriends() {
        return invitedFriends;
    }

    public void setInvitedFriends(List<User> invitedFriends) {
        this.invitedFriends = invitedFriends;
    }

    @JsonIgnore
    @Transient
    public Set<User> getFriends() {
        Set<User> friends = new HashSet<>();
        friends.addAll(acceptedFriends);
        friends.addAll(invitedFriends);
        return friends;
    }

    public void setAcceptedFriends(List<User> acceptedFriends) {
        this.acceptedFriends = acceptedFriends;
    }

    public List<WallMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<WallMessage> messages) {
        this.messages = messages;
    }
}
