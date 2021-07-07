package com.lekstreek.portal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lekstreek.portal.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class RelatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Relatie.class);
        Relatie relatie1 = new Relatie();
        relatie1.setId(UUID.randomUUID());
        Relatie relatie2 = new Relatie();
        relatie2.setId(relatie1.getId());
        assertThat(relatie1).isEqualTo(relatie2);
        relatie2.setId(UUID.randomUUID());
        assertThat(relatie1).isNotEqualTo(relatie2);
        relatie1.setId(null);
        assertThat(relatie1).isNotEqualTo(relatie2);
    }
}
