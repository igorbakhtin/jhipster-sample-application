package com.bakhtin.jhipstertest.service.mapper;


import com.bakhtin.jhipstertest.domain.*;
import com.bakhtin.jhipstertest.service.dto.OperatorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Operator} and its DTO {@link OperatorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OperatorMapper extends EntityMapper<OperatorDTO, Operator> {



    default Operator fromId(String id) {
        if (id == null) {
            return null;
        }
        Operator operator = new Operator();
        operator.setId(id);
        return operator;
    }
}
