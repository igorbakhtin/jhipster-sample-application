package com.bakhtin.jhipstertest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientMapperTest {

    private ClientMapper clientMapper;

    @BeforeEach
    public void setUp() {
        clientMapper = new ClientMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(clientMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(clientMapper.fromId(null)).isNull();
    }
}
