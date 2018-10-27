package edu.lysianok.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "messages")
public class Message implements Comparable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Basic
    @Column(name = "text", nullable = false, length = 255)
    private String text;

    @OneToMany(mappedBy = "messageId")
    private List<Attachment> attachments;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        if (date != null ? !date.equals(message.date) : message.date != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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

    @Override
    public int compareTo(Object o) {
        Message anotherMessage = (Message) o;
        return -this.date.compareTo(anotherMessage.getDate());
    }
}
