package com.bakhtin.jhipstertest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bakhtin.jhipstertest.web.rest.TestUtil;

public class ChatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chat.class);
        Chat chat1 = new Chat();
        chat1.setId("id1");
        Chat chat2 = new Chat();
        chat2.setId(chat1.getId());
        assertThat(chat1).isEqualTo(chat2);
        chat2.setId("id2");
        assertThat(chat1).isNotEqualTo(chat2);
        chat1.setId(null);
        assertThat(chat1).isNotEqualTo(chat2);
    }
}
