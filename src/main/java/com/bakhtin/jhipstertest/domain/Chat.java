package com.bakhtin.jhipstertest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Chat.
 */
@Document(collection = "chat")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "chat")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    @Field("message")
    private Set<Message> messages = new HashSet<>();

    @DBRef
    @Field("client")
    @JsonIgnoreProperties(value = "chats", allowSetters = true)
    private Client client;

    @DBRef
    @Field("operator")
    @JsonIgnoreProperties(value = "chats", allowSetters = true)
    private Operator operator;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Chat messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Chat addMessage(Message message) {
        this.messages.add(message);
        message.setChat(this);
        return this;
    }

    public Chat removeMessage(Message message) {
        this.messages.remove(message);
        message.setChat(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Client getClient() {
        return client;
    }

    public Chat client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Operator getOperator() {
        return operator;
    }

    public Chat operator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chat)) {
            return false;
        }
        return id != null && id.equals(((Chat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chat{" +
            "id=" + getId() +
            "}";
    }
}
