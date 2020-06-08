package com.bakhtin.jhipstertest.service.mapper;


import com.bakhtin.jhipstertest.domain.*;
import com.bakhtin.jhipstertest.service.dto.ChatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chat} and its DTO {@link ChatDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, OperatorMapper.class})
public interface ChatMapper extends EntityMapper<ChatDTO, Chat> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "operator.id", target = "operatorId")
    ChatDTO toDto(Chat chat);

    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "removeMessage", ignore = true)
    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "operatorId", target = "operator")
    Chat toEntity(ChatDTO chatDTO);

    default Chat fromId(String id) {
        if (id == null) {
            return null;
        }
        Chat chat = new Chat();
        chat.setId(id);
        return chat;
    }
}
