package com.bakhtin.jhipstertest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OperatorMapperTest {

    private OperatorMapper operatorMapper;

    @BeforeEach
    public void setUp() {
        operatorMapper = new OperatorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(operatorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(operatorMapper.fromId(null)).isNull();
    }
}
