package com.lekstreek.portal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lekstreek.portal.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class RolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rol.class);
        Rol rol1 = new Rol();
        rol1.setId(UUID.randomUUID());
        Rol rol2 = new Rol();
        rol2.setId(rol1.getId());
        assertThat(rol1).isEqualTo(rol2);
        rol2.setId(UUID.randomUUID());
        assertThat(rol1).isNotEqualTo(rol2);
        rol1.setId(null);
        assertThat(rol1).isNotEqualTo(rol2);
    }
}
