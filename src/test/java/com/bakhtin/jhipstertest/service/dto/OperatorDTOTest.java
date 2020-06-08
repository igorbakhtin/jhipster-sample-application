package com.bakhtin.jhipstertest.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bakhtin.jhipstertest.web.rest.TestUtil;

public class OperatorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperatorDTO.class);
        OperatorDTO operatorDTO1 = new OperatorDTO();
        operatorDTO1.setId("id1");
        OperatorDTO operatorDTO2 = new OperatorDTO();
        assertThat(operatorDTO1).isNotEqualTo(operatorDTO2);
        operatorDTO2.setId(operatorDTO1.getId());
        assertThat(operatorDTO1).isEqualTo(operatorDTO2);
        operatorDTO2.setId("id2");
        assertThat(operatorDTO1).isNotEqualTo(operatorDTO2);
        operatorDTO1.setId(null);
        assertThat(operatorDTO1).isNotEqualTo(operatorDTO2);
    }
}
