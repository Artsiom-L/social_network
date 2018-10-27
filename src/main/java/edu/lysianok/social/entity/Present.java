package edu.lysianok.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "presents")
public class Present implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "present_id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Basic
    @Column(name = "signature", nullable = false, length = 255)
    private String signature;

    @ManyToOne
    @JoinColumn(name = "source", referencedColumnName = "user_id", nullable = false)
    private User sender;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "recipient", referencedColumnName = "user_id", nullable = false)
    private User recipient;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Present presents = (Present) o;

        if (id != null ? !id.equals(presents.id) : presents.id != null) return false;
        if (date != null ? !date.equals(presents.date) : presents.date != null) return false;
        if (name != null ? !name.equals(presents.name) : presents.name != null) return false;
        if (signature != null ? !signature.equals(presents.signature) : presents.signature != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}
