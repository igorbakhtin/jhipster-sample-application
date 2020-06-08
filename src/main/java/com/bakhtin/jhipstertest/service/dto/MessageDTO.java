package com.bakhtin.jhipstertest.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.bakhtin.jhipstertest.domain.Message} entity.
 */
public class MessageDTO implements Serializable {
    
    private String id;

    private String text;


    private String chatId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageDTO)) {
            return false;
        }

        return id != null && id.equals(((MessageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", chatId='" + getChatId() + "'" +
            "}";
    }
}
