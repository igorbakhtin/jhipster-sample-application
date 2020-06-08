package com.bakhtin.jhipstertest.service.mapper;


import com.bakhtin.jhipstertest.domain.*;
import com.bakhtin.jhipstertest.service.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {



    default Client fromId(String id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
