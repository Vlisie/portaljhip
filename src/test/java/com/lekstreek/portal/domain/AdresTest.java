package com.lekstreek.portal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lekstreek.portal.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AdresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adres.class);
        Adres adres1 = new Adres();
        adres1.setId(UUID.randomUUID());
        Adres adres2 = new Adres();
        adres2.setId(adres1.getId());
        assertThat(adres1).isEqualTo(adres2);
        adres2.setId(UUID.randomUUID());
        assertThat(adres1).isNotEqualTo(adres2);
        adres1.setId(null);
        assertThat(adres1).isNotEqualTo(adres2);
    }
}
