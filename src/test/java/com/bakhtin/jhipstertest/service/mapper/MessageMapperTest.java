package com.bakhtin.jhipstertest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageMapperTest {

    private MessageMapper messageMapper;

    @BeforeEach
    public void setUp() {
        messageMapper = new MessageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(messageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(messageMapper.fromId(null)).isNull();
    }
}
