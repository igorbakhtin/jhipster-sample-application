package com.bakhtin.jhipstertest.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.bakhtin.jhipstertest.domain.Chat} entity.
 */
public class ChatDTO implements Serializable {
    
    private String id;


    private String clientId;

    private String operatorId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatDTO)) {
            return false;
        }

        return id != null && id.equals(((ChatDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatDTO{" +
            "id=" + getId() +
            ", clientId='" + getClientId() + "'" +
            ", operatorId='" + getOperatorId() + "'" +
            "}";
    }
}
