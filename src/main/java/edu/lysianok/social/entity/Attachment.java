package edu.lysianok.social.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "attachments")
public class Attachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id", nullable = false)
    private Integer id;

    @Column(name = "message_id", nullable = false)
    private Integer messageId;

    @Basic
    @Column(name = "filename", nullable = false, length = 255)
    private String filename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attachment attachment = (Attachment) o;

        if (id != null ? !id.equals(attachment.id) : attachment.id != null)
            return false;
        if (filename != null ? !filename.equals(attachment.filename) : attachment.filename != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        return result;
    }
}
