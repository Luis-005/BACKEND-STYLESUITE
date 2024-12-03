package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EstablecimientoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstablecimientoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Establecimiento.class);
        Establecimiento establecimiento1 = getEstablecimientoSample1();
        Establecimiento establecimiento2 = new Establecimiento();
        assertThat(establecimiento1).isNotEqualTo(establecimiento2);

        establecimiento2.setId(establecimiento1.getId());
        assertThat(establecimiento1).isEqualTo(establecimiento2);

        establecimiento2 = getEstablecimientoSample2();
        assertThat(establecimiento1).isNotEqualTo(establecimiento2);
    }
}
