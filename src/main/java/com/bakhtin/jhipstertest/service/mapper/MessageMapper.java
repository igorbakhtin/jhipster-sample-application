package com.bakhtin.jhipstertest.service.mapper;


import com.bakhtin.jhipstertest.domain.*;
import com.bakhtin.jhipstertest.service.dto.MessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring", uses = {ChatMapper.class})
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {

    @Mapping(source = "chat.id", target = "chatId")
    MessageDTO toDto(Message message);

    @Mapping(source = "chatId", target = "chat")
    Message toEntity(MessageDTO messageDTO);

    default Message fromId(String id) {
        if (id == null) {
            return null;
        }
        Message message = new Message();
        message.setId(id);
        return message;
    }
}
